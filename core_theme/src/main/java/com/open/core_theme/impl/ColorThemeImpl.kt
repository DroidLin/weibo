package com.open.core_theme.impl

import android.graphics.drawable.Drawable
import com.open.core_theme_interface.theme.IColorTheme
import com.open.core_theme_interface.theme.Theme

class ColorThemeImpl : IColorTheme {
    private var theme: IColorTheme = WhiteTheme()

    override fun isLightModeStatusBar(): Boolean = theme.isLightModeStatusBar

    override fun getStatusBarColor(): Int = theme.statusBarColor

    override fun getNavigationBarColor(): Int = theme.navigationBarColor

    override fun getTextColor(): Int = theme.textColor

    override fun getSecondaryTextColor(): Int = theme.secondaryTextColor

    override fun getPrimaryColor(): Int = theme.primaryColor

    override fun getSecondaryColor(): Int = theme.secondaryColor

    override fun getDrawableForegroundHint(): Int = theme.drawableForegroundHint

    override fun getDrawableTint(): Int = theme.drawableTint

    override fun getWindowBackground(): Int = theme.windowBackground

    override fun getDrawable(resource: Int): Drawable = theme.getDrawable(resource)

    override fun getColor(colorRes: Int): Int = theme.getColor(colorRes)

    override fun setTheme(type: Theme) {
        when (type) {
            Theme.white -> {
                theme = WhiteTheme()
            }
            Theme.black -> {
                theme = BlackTheme()
            }
            Theme.pink -> {
                theme = PinkTheme()
            }
            Theme.custom -> {

            }
        }
    }

}