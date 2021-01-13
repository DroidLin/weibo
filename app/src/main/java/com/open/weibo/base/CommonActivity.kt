package com.open.weibo.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.open.core_base.coroutine.launch
import com.open.core_base.service.ServiceFacade
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

        StatusBarUtil.setStatusBarColor(this, colorThemeWrapper.statusBarColor)

        window.setBackgroundDrawable(ColorDrawable(colorThemeWrapper.windowBackground))

        val decorView = window.decorView
        decorView.systemUiVisibility = decorView.systemUiVisibility
            .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            .or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }
}