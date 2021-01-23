package com.open.weibo.main.fragment

import android.app.Application
import androidx.lifecycle.ViewModelProvider
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

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener? = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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
        vm =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
                .create(HomelineViewModel::class.java)
        vm?.pagedListLiveData?.observe(this) {
            if (adapter is HomelinePagingListAdapter?) {
                (adapter as HomelinePagingListAdapter?)?.submitList(it)
                setRefreshing(false)
            }
        }
    }
}