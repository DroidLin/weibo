package com.open.core_theme.impl

import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.open.core_theme_interface.theme.IColorTheme
import com.open.core_theme_interface.theme.Theme
import org.json.JSONObject

class ColorThemeImpl : IColorTheme {
    private val themeLiveData = MutableLiveData<IColorTheme>()

    private fun getTheme() : IColorTheme = themeLiveData.value!!

    override fun isLightModeStatusBar(): Boolean = getTheme().isLightModeStatusBar

    override fun getStatusBarColor(): Int = getTheme().statusBarColor

    override fun getNavigationBarColor(): Int = getTheme().navigationBarColor

    override fun getTextColor(): Int = getTheme().textColor

    override fun getSecondaryTextColor(): Int = getTheme().secondaryTextColor

    override fun getPrimaryColor(): Int = getTheme().primaryColor

    override fun getSecondaryColor(): Int = getTheme().secondaryColor

    override fun getDrawableForegroundHint(): Int = getTheme().drawableForegroundHint

    override fun getDrawableTint(): Int = getTheme().drawableTint

    override fun getWindowBackground(): Int = getTheme().windowBackground

    override fun getDrawable(resource: Int): Drawable = getTheme().getDrawable(resource)

    override fun getColor(colorRes: Int): Int = getTheme().getColor(colorRes)

    override fun setTheme(type: Theme) {
        when (type) {
            Theme.white -> {
                themeLiveData.setValue(WhiteTheme())
            }
            Theme.black -> {
                themeLiveData.setValue(BlackTheme())
            }
            Theme.pink -> {
                themeLiveData.setValue(PinkTheme())
            }
            Theme.custom -> {
            }
        }
    }

    override fun setThemeChanged(owner: LifecycleOwner, observer: Observer<IColorTheme>) {
        themeLiveData.observe(owner, observer)
    }

    override fun decodeJSON(jsonObject: JSONObject?): IColorTheme = getTheme().decodeJSON(jsonObject)

    override fun needDynamicStatusColor(): Boolean = getTheme().needDynamicStatusColor()

}