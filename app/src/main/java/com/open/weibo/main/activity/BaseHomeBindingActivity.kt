package com.open.weibo.main.activity

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.open.core_base.permission.Permission
import com.open.weibo.R
import com.open.weibo.main.adapter.HomeFragmentAdapter
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.base.BaseBindingActivity
import com.open.weibo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

open class BaseHomeBindingActivity : BaseBindingActivity<ActivityMainBinding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var menuItem: MenuItem? = null
    private var currentFragment: Fragment? = null

    private val fragmentAdapter by lazy { HomeFragmentAdapter(this) }
    private val onPageChangeCallback by lazy {
        object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                val newDisplayFragment = fragmentAdapter.createFragment(position)
                val transaction = supportFragmentManager.beginTransaction()
                if (newDisplayFragment.fragmentManager == supportFragmentManager) {
                    transaction.setMaxLifecycle(newDisplayFragment, Lifecycle.State.RESUMED)
                        .commitNow()
                }

                val current = currentFragment
                if (current != null && current.fragmentManager == supportFragmentManager) {
                    transaction
                        .setMaxLifecycle(current, Lifecycle.State.STARTED).commitNow()
                }
                currentFragment = newDisplayFragment

                if (menuItem != null) {
                    menuItem?.isChecked = false
                }

                menuItem = main_bottom_navigationView.menu.getItem(position)
                menuItem?.isChecked = true
            }
        }
    }

    override fun initialBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override suspend fun initViews() {
        center_ViewPager2.registerOnPageChangeCallback(onPageChangeCallback)
        main_bottom_navigationView.setOnNavigationItemSelectedListener(this)
        center_ViewPager2.adapter = fragmentAdapter

        val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
        service.setThemeChanged(this,this)
    }

    override suspend fun loadData() {
        Permission.check(this, 0x1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
        configUIThemes()
        requireBinding().invalidateAll()
    }
}