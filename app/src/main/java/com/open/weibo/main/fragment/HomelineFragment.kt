package com.open.weibo.main.fragment

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.fragment.AbsListFragment
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.R
import com.open.weibo.main.adapter.HomelinePagingListAdapter
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.LayoutContainerLogoBinding
import com.open.weibo.statuses.upload.activity.UploadStatusesActivity
import com.open.weibo.stratagy.FloatingAnimStratagy
import com.open.weibo.utils.EmojiUtils
import com.open.weibo.utils.ProjectConfig
import com.open.weibo.view.HItemDecoration
import com.open.weibo.main.vm.HomelineViewModel

class HomelineFragment : AbsListFragment(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener, Observer<IColorTheme> {

    private val vm: HomelineViewModel by lazy { ViewModelProviders.of(requireActivity()).get(HomelineViewModel::class.java) }

    private val floatingAnimStratagy: FloatingAnimStratagy by lazy { FloatingAnimStratagy() }
    private var topHeader: ViewGroup? = null

    private val scaleLiveData: MutableLiveData<Float> by lazy {
        val floatLiveData = MutableLiveData<Float>()
        floatLiveData.observe(this) {
            val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
            val alpha = it
            if (service.needDynamicStatusColor()) {
                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    if (alpha > 0.25f) {
                        StatusBarUtil.setStatusBarDarkTheme(activity, service.isLightModeStatusBar)
                    } else {
                        StatusBarUtil.setStatusBarDarkTheme(activity, !service.isLightModeStatusBar)
                    }
                }
            }
            if (alpha > 0.15f) {
                floatingAnimStratagy.fadeIn()
            } else {
                floatingAnimStratagy.fadeOff()
            }
        }
        floatLiveData
    }

    override fun adjustRootView(rootView: ViewGroup) {
        val inflater = LayoutInflater.from(requireContext())
        val binding = LayoutContainerLogoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.clickListener = this
        topHeader = binding.root as ViewGroup
        topHeader?.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        rootView.addView(topHeader)
        floatingAnimStratagy.setTargetView(topHeader)
    }

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener = this

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.clipChildren = false
        recyclerView.clipToPadding = false
        recyclerView.setPadding(
            recyclerView.paddingTop,
            StatusBarUtil.getStatusBarHeight(requireContext()),
            recyclerView.paddingRight,
            recyclerView.paddingBottom
        )
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
                    if (firstDisplayPosition <= 1) {
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

    override suspend fun initViews() {
        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        colorThemeWrapper.setThemeChanged(this, this)

        themeTopHeader()

        vm.pagedListLiveData.observe(this) {
            if (adapter is HomelinePagingListAdapter?) {
                (adapter as HomelinePagingListAdapter?)?.submitList(it)
                setRefreshing(false)
            }
        }
    }

    private fun themeTopHeader() {
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
        vm.invalidate(false)
    }

    override fun onRefresh() {
        vm.invalidate(true)
    }

    override fun initIntentFilter(filter: IntentFilter) {
        filter.addAction(ProjectConfig.LOGIN_GRANTED_EVENT)
    }

    override fun globalBroadcast(intent: Intent?) {
        val action = intent?.action ?: return

        when (action) {
            ProjectConfig.LOGIN_GRANTED_EVENT -> {
                EmojiUtils.getInstance().init()
                vm.invalidate(true)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.upload -> {
                UploadStatusesActivity.launch(v.context)
            }
        }
    }

    override fun onChanged(t: IColorTheme) {
        themeTopHeader()
        val size = adapter?.itemCount ?: 0
        adapter?.notifyItemRangeChanged(0, size, "theme")
    }
}