package com.open.weibo.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.open.core_base.coroutine.launch
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.OSUtils
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme

abstract class CommonActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var binding: T? = null

    abstract fun initialBinding(): T

    protected fun requireBinding(): T = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configUIThemes()

        binding = initialBinding()
        binding?.lifecycleOwner = this

        setContentView(binding?.root)

        launch {
            initViews()

            loadData()
        }
    }

    open suspend fun initViews() {}

    open suspend fun loadData() {}

    private fun configUIThemes() {
        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        StatusBarUtil.setStatusBarDarkTheme(this, colorThemeWrapper.isLightModeStatusBar)
        window.addFlags(
            if (OSUtils.isEmui) {
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            } else {
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            }
        )
        window.statusBarColor = colorThemeWrapper.statusBarColor
        window.navigationBarColor = colorThemeWrapper.navigationBarColor
        window.setBackgroundDrawable(ColorDrawable(colorThemeWrapper.windowBackground))

        val decorView = window.decorView
        decorView.systemUiVisibility = decorView.systemUiVisibility
            .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            .or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            .or(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }
}