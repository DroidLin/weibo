package com.open.core_theme.impl

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.core_theme_interface.theme.Theme

class BlackTheme : IColorTheme {
    private val textColor: Int = Color.WHITE
    private val secondaryTextColor: Int = Color.GRAY
    private val primaryColor: Int = Color.DKGRAY
    private val windowBackground = Color.BLACK
    private val drawableHint: Int = Color.parseColor("#7f000000")
    private val statusBackground: Int = Color.TRANSPARENT
    private val navigationBarColor: Int = Color.TRANSPARENT

    override fun isLightModeStatusBar(): Boolean = false

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = Color.TRANSPARENT

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = primaryColor.and(0x7FFFFFFF)

    override fun getDrawableHint(): Int = drawableHint

    override fun getWindowBackground(): Int = windowBackground

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(resourceId: Int): Drawable {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getDrawable(resourceId)
    }

    override fun getColor(colorRes: Int): Int {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getColor(colorRes)
    }

    override fun setTheme(type: Theme) {}

}


class WhiteTheme : IColorTheme {
    private val textColor: Int = Color.BLACK
    private val secondaryTextColor: Int = Color.GRAY
    private val primaryColor: Int = Color.LTGRAY
    private val windowBackground = Color.WHITE
    private val drawableHint: Int = Color.TRANSPARENT
    private val statusBackground: Int = Color.TRANSPARENT

    override fun isLightModeStatusBar(): Boolean = true

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = Color.TRANSPARENT

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = primaryColor.and(0x7FFFFFFF)

    override fun getDrawableHint(): Int = drawableHint

    override fun getWindowBackground(): Int = windowBackground

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(resourceId: Int): Drawable {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getDrawable(resourceId)
    }

    override fun getColor(colorRes: Int): Int {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getColor(colorRes)
    }

    override fun setTheme(type: Theme) {}

}


class PinkTheme : IColorTheme {
    private val textColor: Int = Color.WHITE
    private val secondaryTextColor: Int = Color.GRAY
    private val primaryColor: Int = Color.parseColor("FFEC407A")
    private val windowBackground = Color.BLACK
    private val drawableHint: Int = Color.TRANSPARENT
    private val statusBackground: Int = Color.TRANSPARENT

    override fun isLightModeStatusBar(): Boolean = false

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = Color.TRANSPARENT

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = primaryColor.and(0x7FFFFFFF)

    override fun getDrawableHint(): Int = drawableHint

    override fun getWindowBackground(): Int = windowBackground

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(resourceId: Int): Drawable {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getDrawable(resourceId)
    }

    override fun getColor(colorRes: Int): Int {
        val resource =
            ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
        return resource.getColor(colorRes)
    }

    override fun setTheme(type: Theme) {}

}