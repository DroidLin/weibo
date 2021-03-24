package com.open.core_theme.impl

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.core_theme_interface.theme.Theme
import org.json.JSONObject

class BlackTheme : IColorTheme {
    private val textColor: Int = Color.parseColor("#CCCCCC")
    private val secondaryTextColor: Int = textColor.and(0x7FFFFFFF)
    private val primaryColor: Int = Color.parseColor("#2C2C2C")
    private val secondaryColor: Int = primaryColor.and(0x7FFFFFFF)
    private val windowBackground = Color.BLACK
    private val drawableHint: Int = Color.parseColor("#7f000000")
    private val statusBackground: Int = primaryColor
    private val navigationColor: Int = textColor
    private val statusBarColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(statusBackground)
    private val windowBackgroundColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(windowBackground)

    override fun isLightModeStatusBar(): Boolean = statusBarColor.isLightColor()

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = navigationColor

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = secondaryColor

    override fun getDrawableForegroundHint(): Int = drawableHint

    override fun getDrawableTint(): Int = textColor

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

    override fun setThemeChanged(owner: LifecycleOwner?, observer: Observer<IColorTheme>?) {}

    override fun decodeJSON(jsonObject: JSONObject?): IColorTheme = this

    override fun needDynamicStatusColor(): Boolean =
        statusBarColor.isLightColor() != windowBackgroundColor.isLightColor()

}


class WhiteTheme : IColorTheme {
    private val textColor: Int = Color.BLACK
    private val secondaryTextColor: Int = textColor.and(0x7FFFFFFF)
    private val primaryColor: Int = Color.WHITE
    private val secondaryColor: Int = primaryColor.and(0x7FFFFFFF)
    private val windowBackground = Color.parseColor("#FFE8E8E8")
    private val drawableHint: Int = Color.TRANSPARENT
    private val statusBackground: Int = Color.WHITE
    private val navigationColor: Int = textColor
    private val statusBarColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(statusBackground)
    private val windowBackgroundColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(windowBackground)

    override fun isLightModeStatusBar(): Boolean = statusBarColor.isLightColor()

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = navigationColor

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = secondaryColor

    override fun getDrawableForegroundHint(): Int = drawableHint

    override fun getDrawableTint(): Int = textColor

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

    override fun setThemeChanged(owner: LifecycleOwner?, observer: Observer<IColorTheme>?) {}

    override fun decodeJSON(jsonObject: JSONObject?): IColorTheme = this

    override fun needDynamicStatusColor(): Boolean =
        statusBarColor.isLightColor() != windowBackgroundColor.isLightColor()

}


class PinkTheme : IColorTheme {
    private val textColor: Int = Color.BLACK
    private val secondaryTextColor: Int = textColor.and(0x7FFFFFFF)
    private val primaryColor: Int = Color.parseColor("#FFD5DB")
    private val secondaryColor: Int = primaryColor.and(0x7FFFFFFF)
    private val windowBackground = Color.WHITE
    private val drawableHint: Int = Color.TRANSPARENT
    private val statusBackground: Int = primaryColor
    private val navigationColor: Int = primaryColor
    private val statusBarColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(statusBackground)
    private val windowBackgroundColor: com.open.core_theme.impl.Color =
        com.open.core_theme.impl.Color.parseColor(windowBackground)

    override fun isLightModeStatusBar(): Boolean = statusBarColor.isLightColor()

    override fun getStatusBarColor(): Int = statusBackground

    override fun getNavigationBarColor(): Int = navigationColor

    override fun getTextColor(): Int = textColor

    override fun getSecondaryTextColor(): Int = secondaryTextColor

    override fun getPrimaryColor(): Int = primaryColor

    override fun getSecondaryColor(): Int = secondaryColor

    override fun getDrawableForegroundHint(): Int = drawableHint

    override fun getDrawableTint(): Int = textColor

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

    override fun setThemeChanged(owner: LifecycleOwner?, observer: Observer<IColorTheme>?) {}

    override fun decodeJSON(jsonObject: JSONObject?): IColorTheme = this

    override fun needDynamicStatusColor(): Boolean =
        statusBarColor.isLightColor() != windowBackgroundColor.isLightColor()

}