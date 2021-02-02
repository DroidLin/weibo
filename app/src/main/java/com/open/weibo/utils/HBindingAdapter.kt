package com.open.weibo.utils

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.open.core_base.service.ServiceFacade
import com.open.core_image_interface.interfaces.IImage
import com.open.core_theme_interface.theme.IColorTheme

object HBindingAdapter {
    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun isRefreshing(refreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
        refreshLayout.isRefreshing = isRefreshing
    }

    @JvmStatic
    @BindingAdapter("statefulPlaceHolder")
    fun statefulPlaceHolder(view: Button, placeholderColor: Int) {
        val drawable = view.background
        drawable.setTint(placeholderColor)
    }

    @JvmStatic
    @BindingAdapter("themeTextView")
    fun themeTextView(view: TextView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.setTextColor(colorThemeWrapper.textColor)
    }

    @JvmStatic
    @BindingAdapter("themeSecondaryTextView")
    fun themeSecondaryTextView(view: TextView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.setTextColor(colorThemeWrapper.secondaryTextColor)
    }

    @JvmStatic
    @BindingAdapter("themeImageView")
    fun themeImageView(view: ImageView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.foreground = ColorDrawable(colorThemeWrapper.drawableForegroundHint)
    }

    @JvmStatic
    @BindingAdapter("themeImageViewTint")
    fun themeImageViewTint(view: ImageView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        val drawable = view.drawable
        drawable?.setTint(colorThemeWrapper.drawableTint)
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("themeBottomNavigation")
    fun themeBottomNavigation(view: BottomNavigationView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.background = ColorDrawable(Color.TRANSPARENT)
        val colorStateList = ColorStateList(
            arrayOf(createState(R.attr.state_checked), createState(0)),
            intArrayOf(colorThemeWrapper.primaryColor, colorThemeWrapper.secondaryColor)
        )
        view.itemIconTintList = colorStateList
        view.itemTextColor = colorStateList
    }

    @JvmStatic
    @BindingAdapter("stateBackground")
    fun stateClickBackground(view: View?, `object`: Any?) {
        view?.isClickable = true
        view?.isFocusable = true

        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        val background = StateListDrawable()
        background.addState(createState(R.attr.state_pressed), ColorDrawable(colorThemeWrapper.secondaryColor and 0x20FFFFFF))
        background.addState(createState(R.attr.state_enabled), ColorDrawable(Color.TRANSPARENT))
        view?.background = background
    }

    @JvmStatic
    @BindingAdapter(value = ["cornerUrl"])
    fun setCornerImageUrl(view: SimpleDraweeView?, url: String?) {
        val service = ServiceFacade.getInstance()[IImage::class.java]
        service.loadRadius(url, view, 15f)
    }

    @JvmStatic
    @BindingAdapter(value = ["backgroundTint"])
    fun viewBackgroundTint(view: View, `object`: Any?) {
        val service = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.background = ColorDrawable(service.secondaryColor)
    }


    private fun createState(type: Int): IntArray {
        return intArrayOf(type)
    }

    @JvmStatic
    fun countFormat(count: Long): String {
        return when {
            count > 10000 -> {
                "${count / 10000.0}万"
            }
            else -> "$count"
        }
    }

    @JvmStatic
    fun intToString(num: Int): String {
        return "$num"
    }

    @JvmStatic
    fun longToString(num: Long): String {
        return "$num"
    }
}