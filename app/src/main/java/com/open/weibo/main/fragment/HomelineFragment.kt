package com.open.weibo.main.fragment

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.fragment.AbsListFragment
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.adapter.HomelinePagingListAdapter
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.LayoutContainerLogoBinding
import com.open.weibo.utils.ProjectConfig
import com.open.weibo.view.HItemDecoration
import com.open.weibo.vm.HomelineViewModel

class HomelineFragment : AbsListFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var topHeader: ViewGroup? = null

    private val scaleLiveData: MutableLiveData<Float> by lazy {
        val floatLiveData = MutableLiveData<Float>()
        floatLiveData.observeForever {
            val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
            var alpha = it
            if (service.needDynamicStatusColor()) {
                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    if (alpha > 0.5f) {
                        StatusBarUtil.setStatusBarDarkTheme(activity, service.isLightModeStatusBar)
                    } else {
                        StatusBarUtil.setStatusBarDarkTheme(activity, !service.isLightModeStatusBar)

                    }
                }
            }
            if (alpha > 1f) {
                alpha = 1f
            }
            topHeader?.alpha = alpha
        }
        floatLiveData
    }

    override fun getCustomChildView(container: ViewGroup): View? {
        val inflater = LayoutInflater.from(requireContext())
        topHeader = LayoutContainerLogoBinding.inflate(inflater, container, false).root as ViewGroup
        return topHeader
    }

    private var vm: HomelineViewModel? = null

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.clipChildren = false
        recyclerView.clipToPadding = false
        recyclerView.setPadding(0, StatusBarUtil.getStatusBarHeight(requireContext()), 0, 0)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(HItemDecoration())
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager
                var alpha = 1.0f
                if (layoutManager is LinearLayoutManager) {
                    val topChildView = layoutManager.findViewByPosition(0) ?: return
                    val firstDisplayPosition = layoutManager.findFirstVisibleItemPosition()
                    if (firstDisplayPosition <= 2) {
                        alpha -= (topChildView.bottom.toFloat() / ((topChildView.height.toFloat())))
                        scaleLiveData.postValue(alpha)
                    }
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

    override suspend fun loadInitial() {
        val topheader = topHeader ?: return
        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        topHeader?.setBackgroundColor(colorThemeWrapper.statusBarColor)
        topHeader?.setPadding(0, StatusBarUtil.getStatusBarHeight(requireContext()), 0, 0)

        val isLightColor = colorThemeWrapper.isLightModeStatusBar
        val color =
            if (isLightColor) {
                Color.BLACK
            } else {
                Color.WHITE
            }
        for (index in 0 until topheader.childCount) {
            val child = topheader.getChildAt(index)
            if (child is TextView) {
                child.setTextColor(color)
            } else if (child is ImageView) {
                val drawable = child.drawable
                drawable?.setTint(color)
                child.setImageDrawable(drawable)
            }
        }
    }

    override suspend fun loadData() {
        setRefreshing(true)
        initViewModel()
    }

    override fun onRefresh() {
        initViewModel()
    }

    private fun initViewModel() {
        val contextWrapper = ServiceFacade.getInstance().get(IContext::class.java)
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(contextWrapper.application)
            .create(HomelineViewModel::class.java)
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