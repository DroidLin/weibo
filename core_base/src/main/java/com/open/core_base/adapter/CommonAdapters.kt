package com.open.core_base.adapter

import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.core.util.valueIterator
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import java.io.IOException
import kotlin.coroutines.CoroutineContext

abstract class CommonPagingAdapter<T : Any, VH : CommonViewHolder<*, T>>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffUtil) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        holder.close()
    }
}

abstract class CommonAdapter<T> : RecyclerView.Adapter<CommonViewHolder<*, Any>>() {

    private val displayList: MutableList<T> = ArrayList()

    fun submitList(items: List<T>): Boolean {
        if (items.isNotEmpty()) {
            return submitList(items, true)
        }
        Log.e("CommonAdapter", "items can not be empty")
        return false
    }

    fun submitList(items: List<T>, append: Boolean): Boolean {
        if (items.isEmpty()) return false
        val previousCount = displayList.size
        if (!append) {
            displayList.clear()
        }
        displayList.addAll(items)
        notifyItemRangeInserted(previousCount, items.size)
        return true
    }

    override fun onBindViewHolder(holder: CommonViewHolder<*, Any>, position: Int) {
        val item = displayList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = displayList.size

    override fun onViewDetachedFromWindow(holder: CommonViewHolder<*, Any>) {
        holder.close()
    }

    protected fun getItem(position: Int): T? =
        if (position < 0 || position > displayList.size) {
            null
        } else {
            displayList[position]
        }
}

abstract class CommonViewHolder<B : ViewDataBinding, T : Any>(protected val binding: B) :
    RecyclerView.ViewHolder(binding.root), ViewHolderBinder<T>, View.OnClickListener, Closeable {
    private val mBagOfTags: SparseArray<Any> = SparseArray<Any>()

    fun getCurrentCoroutineScope(): CoroutineScope? {
        return mBagOfTags.get(JOB_KEY.hashCode()) as CoroutineScope?
    }

    fun <T> getTag(key: String): T? {
        return mBagOfTags[key.hashCode()] as T?
    }

    fun <T> setIfAbsent(key: String, value: T): T {
        val previous = mBagOfTags[key.hashCode()]
        synchronized(mBagOfTags) {
            if (previous == null) {
                mBagOfTags.put(key.hashCode(), value)
            }
        }
        val result = previous ?: value
        return result as T
    }

    override fun close() {
        for (obj in mBagOfTags.valueIterator()) {
            closeObjWithRuntimeException(obj)
        }
    }
}

interface ViewHolderBinder<T> {
    fun bind(item: T?) {
        throw IllegalStateException("You must override bind() method to make viewHolder instance make sense")
    }
}

private const val MAIN_HANDLER_KEY = "com.open.core_base.adapter.CommonAdapter.mainHandler_KEY"
private const val JOB_KEY = "com.open.core_base.adapter.CommonAdapter.JOB_KEY"

val CommonViewHolder<*, *>.viewHolderScope: CoroutineScope
    get() {
        val scope: CoroutineScope? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setIfAbsent(
            JOB_KEY,
            ViewHolderCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        )
    }

internal class ViewHolderCoroutineScope(context: CoroutineContext) : CoroutineScope, Closeable {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}

fun closeObjWithRuntimeException(obj: Any) {
    if (obj is Closeable) {
        try {
            obj.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}