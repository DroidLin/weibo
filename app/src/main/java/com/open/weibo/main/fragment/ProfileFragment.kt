package com.open.weibo.main.fragment

import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.open.core_base.fragment.AbsListFragment
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.adapter.ProfileAdapter
import com.open.weibo.utils.ProjectConfig
import com.open.weibo.view.HItemDecoration
import java.util.*

class ProfileFragment : AbsListFragment(), Observer<IColorTheme> {
    override fun enableRefresh(): Boolean = false

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.clipChildren = false
        recyclerView.clipToPadding = false
        recyclerView.setPadding(0, StatusBarUtil.getStatusBarHeight(requireContext()), 0, 0)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(HItemDecoration(30))
    }

    override fun initAdapter(): RecyclerView.Adapter<*> = ProfileAdapter()

    override suspend fun initViews() {
        val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
        service.setThemeChanged(this, this)
    }

    override fun initIntentFilter(filter: IntentFilter) {
        filter.addAction(ProjectConfig.LOGIN_GRANTED_EVENT)
    }

    override fun globalBroadcast(intent: Intent?) {
        val action = intent?.action ?: return

        when (action) {
            ProjectConfig.LOGIN_GRANTED_EVENT -> {
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onChanged(t: IColorTheme) {
        val size = adapter?.itemCount ?: 0
        adapter?.notifyItemRangeChanged(0, size, "theme")
    }
}