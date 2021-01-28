package com.open.core_base.activity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

abstract class BaseViewPagerActivity<M, V : View> : CommonActivity(),
    IViewPagerAdapterHelper<M, V> {

    protected var root: View? = null
    private var adapter: ViewPagerAdapter<M, V>? = null

    override fun getRootView(): View {
        if (root == null) {
            val viewPager = ViewPager2(this)
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            viewPager.layoutParams = params
            root = viewPager
        }
        return root!!
    }

    override suspend fun initViews() {
        val rootView = root
        if (rootView != null && rootView is ViewPager2) {
            adapter = ViewPagerAdapter(this)
            rootView.adapter = adapter
        }
    }
}

class ViewPagerAdapter<M, V : View>(private val viewPagerHelper: IViewPagerAdapterHelper<M, V>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = viewPagerHelper.getViewItem()
        return ViewPagerHolder<V>(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meta: M? = viewPagerHelper.getMetaData(position)
        if (meta != null) {
            viewPagerHelper.bind(holder.itemView as V, meta)
        }
    }

    override fun getItemCount(): Int = viewPagerHelper.getCount()

    private class ViewPagerHolder<V>(view: View) : RecyclerView.ViewHolder(view)
}

interface IViewPagerAdapterHelper<M, V : View> {

    fun getCount(): Int

    fun getViewItem(): V

    fun getMetaData(position: Int): M?

    fun bind(view: V, data: M)
}