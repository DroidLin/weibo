package com.open.weibo.statuses.detail.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.fragment.AbsListFragment
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.bean.Comment
import com.open.weibo.statuses.detail.adapter.CommentPagingAdapter
import com.open.weibo.statuses.detail.popup.ReplyWindow
import com.open.weibo.statuses.detail.vm.ReplyViewModel
import com.open.weibo.statuses.detail.vm.StatusesDetailViewModel

class CommentFragment : AbsListFragment(), SwipeRefreshLayout.OnRefreshListener,
    Observer<IColorTheme> {

    private val replyWindow by lazy { ReplyWindow.launch(requireActivity()) }
    private val replyVm by lazy {
        ViewModelProviders.of(requireActivity()).get(ReplyViewModel::class.java)
    }
    private val vm by lazy {
        ViewModelProviders.of(requireActivity()).get(StatusesDetailViewModel::class.java)
    }

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    override fun initAdapter(): RecyclerView.Adapter<*> =
        CommentPagingAdapter(
            replyWindow,
            requireActivity(),
            object : DiffUtil.ItemCallback<Comment>() {
                override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                    return oldItem.text == newItem.text
                }
            })

    override suspend fun initViews() {
        replyVm.statuses = vm.statuses

        val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
        service.setThemeChanged(this, this)

        vm.pagedListLiveData.observe(this) {
            val adapter = adapter
            if (adapter is CommentPagingAdapter?) {
                adapter?.submitList(it)
            }
            setRefreshing(false)
        }
        replyVm.replyStatusesDataSource.mediator.observe(this) {
            vm.invalidateList()
        }
        replyVm.replyCommentDataSource.mediator.observe(this) {
            vm.invalidateList()
        }
    }

    override suspend fun loadData() {
        setRefreshing(true)
        vm.setStatusesId()
    }

    override fun onRefresh() {
        setRefreshing(false)
    }

    override fun onChanged(t: IColorTheme) {
        val size = adapter?.itemCount ?: 0
        adapter?.notifyItemRangeChanged(0, size, "theme")
    }
}