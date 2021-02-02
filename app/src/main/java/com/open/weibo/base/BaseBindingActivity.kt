package com.open.weibo.base

import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.open.core_base.activity.CommonBindingActivity
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.OSUtils
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme

abstract class BaseBindingActivity<T : ViewDataBinding> : CommonBindingActivity<T>(), Observer<IColorTheme> {
    override fun configUIThemes() {
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
        super.configUIThemes()
    }

    override fun onChanged(t: IColorTheme) {}
}