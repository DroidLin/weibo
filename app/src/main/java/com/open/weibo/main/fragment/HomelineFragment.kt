package com.open.weibo.main.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.fragment.AbsListFragment
import com.open.weibo.adapter.HomelinePagingListAdapter
import com.open.weibo.bean.Statuses
import com.open.weibo.vm.HomelineViewModel

class HomelineFragment : AbsListFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var vm: HomelineViewModel? = null

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            val dividerHeight = 25
            val paint: Paint = Paint()

            init {
                paint.color = Color.LTGRAY.and(0x7FFFFFFF)
            }

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = dividerHeight
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                val childCount = parent.childCount
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight

                for (i in 0 until childCount - 1) {
                    val view = parent.getChildAt(i)
                    val top = view.bottom.toFloat()
                    val bottom = (view.bottom + dividerHeight).toFloat()
                    c.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
                }
            }
        })
    }

    override fun initAdapter(): RecyclerView.Adapter<*> =
        HomelinePagingListAdapter(
            object : DiffUtil.ItemCallback<Statuses>() {
                override fun areItemsTheSame(oldItem: Statuses, newItem: Statuses): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Statuses, newItem: Statuses): Boolean =
                    oldItem.id == newItem.id
            }
        )

    override suspend fun loadData() {
        initViewModel()
    }

    override fun onRefresh() {
        initViewModel()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(requireActivity())[HomelineViewModel::class.java]
        vm?.pagedListLiveData?.observe(this) {
            if (adapter is HomelinePagingListAdapter?) {
                (adapter as HomelinePagingListAdapter?)?.submitList(it)
                setRefreshing(false)
            }
        }
    }
}