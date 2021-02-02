package com.open.weibo.main.activity

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.open.core_base.permission.Permission
import com.open.weibo.R
import com.open.weibo.adapter.HomeFragmentAdapter
import com.open.core_base.activity.CommonBindingActivity
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.base.BaseBindingActivity
import com.open.weibo.databinding.ActivityMainBinding
import com.open.weibo.login.activity.AuthorizationBindingActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseHomeBindingActivity : BaseBindingActivity<ActivityMainBinding>(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var menuItem: MenuItem? = null
    private val fragmentAdapter by lazy { HomeFragmentAdapter(supportFragmentManager) }

    override fun initialBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override suspend fun initViews() {
        center_ViewPager2.addOnPageChangeListener(this)
        main_bottom_navigationView.setOnNavigationItemSelectedListener(this)
        center_ViewPager2.adapter = fragmentAdapter
    }

    override suspend fun loadData() {
        Permission.check(this, 0x1)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (menuItem != null) {
            menuItem?.isChecked = false
        }

        menuItem = main_bottom_navigationView.menu.getItem(position)
        menuItem?.isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.headline -> {
            center_ViewPager2.currentItem = 0
            true
        }
        R.id.mine -> {
            center_ViewPager2.currentItem = 1
            true
        }
        else -> {
            false
        }
    }

    override fun onChanged(t: IColorTheme) {
        requireBinding().notifyChange()
    }
}