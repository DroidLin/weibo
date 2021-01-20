package com.open.weibo.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.open.core_base.fragment.CommonFragment
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.weibo.adapter.HomelinePagingListAdapter
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.FragmentHomelineBinding
import com.open.weibo.vm.HomelineViewModel
import kotlinx.android.synthetic.main.fragment_homeline.*

class HomelineFragment : CommonFragment<FragmentHomelineBinding>() {

    private val vm: HomelineViewModel by lazy {
        val iContext = ServiceFacade.getInstance().get(IContext::class.java)
        ViewModelProvider.AndroidViewModelFactory.getInstance(iContext.application)
            .create(HomelineViewModel::class.java)
    }

    private val adapter: HomelinePagingListAdapter by lazy {
        HomelinePagingListAdapter(
            object : DiffUtil.ItemCallback<Statuses>() {
                override fun areItemsTheSame(oldItem: Statuses, newItem: Statuses): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Statuses, newItem: Statuses): Boolean =
                    oldItem.id == newItem.id
            }
        )
    }

    override fun initialBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomelineBinding = FragmentHomelineBinding.inflate(inflater, container, false)

    override fun isLightStatusBar(): Boolean = true

    override suspend fun loadInitialize() {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        requireBinding().vm = vm
    }

    override suspend fun loadData() {
        vm.pagedListLiveData.observe(this) {
            adapter.submitList(it)
        }
    }
}