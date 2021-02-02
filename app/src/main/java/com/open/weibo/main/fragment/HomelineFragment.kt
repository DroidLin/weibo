package com.open.weibo.main.fragment

import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.fragment.AbsListFragment
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.weibo.adapter.HomelinePagingListAdapter
import com.open.weibo.bean.Statuses
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.utils.ProjectConfig
import com.open.weibo.view.HItemDecoration
import com.open.weibo.vm.HomelineViewModel

class HomelineFragment : AbsListFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var vm: HomelineViewModel? = null

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(HItemDecoration())
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
        val contextWrapper = ServiceFacade.getInstance().get(IContext::class.java)
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(contextWrapper.application).create(HomelineViewModel::class.java)
        vm?.pagedListLiveData?.observe(this) {
            if (adapter is HomelinePagingListAdapter?) {
                (adapter as HomelinePagingListAdapter?)?.submitList(it)
                setRefreshing(false)
            }
        }
    }

    override fun initIntentFilter(filter: IntentFilter) {
        filter.addAction(ProjectConfig.LOGIN_GRANTED_EVENT)
    }

    override fun globalBroadcast(intent: Intent?) {
        val action = intent?.action ?: return

        when (action) {
            ProjectConfig.LOGIN_GRANTED_EVENT -> {
                initViewModel()
            }
        }
    }

}