package com.open.weibo.utils

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.open.core_base.service.ServiceFacade
import com.open.core_image_interface.interfaces.IImage
import com.open.core_theme_interface.theme.IColorTheme
import java.text.DateFormat
import java.util.regex.Pattern

object HBindingAdapter {
    @JvmStatic
    fun parseStatusesString(text: String?): SpannableString? {
        if (text == null) {
            return null
        }
        val pattern =
            Pattern.compile("((#).*?(#))|((@).*?(\\ ))|((\\[).*?(\\]))|((https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|])")
        val spannableString = SpannableString(text)
        val matcher = pattern.matcher(text)
        while (matcher.find()) {
            val colorHint = matcher.group(1)
            if (colorHint != null) {
                val start = matcher.start(1)
                val end = matcher.end(1)
                val span = ForegroundColorSpan(Color.parseColor("#FF3637"))
                spannableString.setSpan(
                    span, start, end,
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            val colorHintAt = matcher.group(4)
            if (colorHintAt != null) {
                val start = matcher.start(4)
                val end = matcher.end(4)
                val span = ForegroundColorSpan(Color.parseColor("#1AEBFF"))
                spannableString.setSpan(
                    span, start, end,
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            val emoji = matcher.group(7)
            if (emoji != null) {
                val start = matcher.start(7)
                val end = matcher.end(7)
                val phrase = text.substring(start, end)
                val emojiIcon = EmojiUtils.getInstance().match(phrase) ?: continue
                emojiIcon.setBounds(0, 0, 75, 75)
                val imageSpan = ImageSpan(emojiIcon)
                spannableString.setSpan(
                    imageSpan,
                    start,
                    end,
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            val url = matcher.group(10)
            if (url != null) {
                val start = matcher.start(10)
                val end = matcher.end(10)
                val clickableSpan = URLSpan(url)
                spannableString.setSpan(
                    clickableSpan,
                    start,
                    end,
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }

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
    @BindingAdapter("themeTextView", "isFavorite", requireAll = false)
    fun themeTextView(view: TextView, `object`: Any?, isFavorite: Boolean = false) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.setTextColor(
            if (isFavorite) {
                Color.RED
            } else {
                colorThemeWrapper.textColor
            }
        )
    }

    @JvmStatic
    @BindingAdapter("themeSecondaryTextView")
    fun themeSecondaryTextView(view: TextView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.setTextColor(colorThemeWrapper.secondaryTextColor)
    }

    @JvmStatic
    @BindingAdapter(value = ["themeImageView"])
    fun themeImageView(view: SimpleDraweeView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.foreground = ColorDrawable(colorThemeWrapper.drawableForegroundHint)
    }

    @JvmStatic
    @BindingAdapter("themeImageViewTint", "isFavorite", requireAll = false)
    fun themeImageViewTint(view: ImageView, drawable: Drawable?, isFavorite: Boolean = false) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        drawable?.setTint(
            if (isFavorite) {
                Color.RED
            } else {
                colorThemeWrapper.drawableTint
            }
        )
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("themeBottomNavigation")
    fun themeBottomNavigation(view: BottomNavigationView, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.background = ColorDrawable(colorThemeWrapper.primaryColor)
        val colorStateList = ColorStateList(
            arrayOf(createState(R.attr.state_checked), createState(0)),
            intArrayOf(
                colorThemeWrapper.textColor,
                colorThemeWrapper.secondaryTextColor
            )
        )
        view.itemIconTintList = colorStateList
        view.itemTextColor = colorStateList
    }

    @JvmStatic
    @BindingAdapter("themeEditText")
    fun themeEditText(view: EditText?, `object`: Any?) {
        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        view?.setTextColor(colorThemeWrapper.textColor)
        view?.setHintTextColor(colorThemeWrapper.secondaryTextColor)
    }

    @JvmStatic
    @BindingAdapter("stateBackground")
    fun stateClickBackground(view: View?, `object`: Any?) {
        view?.isClickable = true
        view?.isFocusable = true

        val colorThemeWrapper = ServiceFacade.getInstance()[IColorTheme::class.java]
        val background = StateListDrawable()
        background.addState(
            createState(R.attr.state_pressed),
            ColorDrawable(colorThemeWrapper.windowBackground.and(0x30FFFFFF))
        )
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


    @JvmStatic
    @BindingAdapter(value = ["primaryBackgroundTint"])
    fun viewPrimaryBackgroundTint(view: View, `object`: Any?) {
        val service = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.background = ColorDrawable(service.primaryColor)
    }


    @JvmStatic
    @BindingAdapter("cardViewBackgroundTint")
    fun cardViewBackgroundTint(view: CardView, `object`: Any?) {
        val service = ServiceFacade.getInstance()[IColorTheme::class.java]
        view.radius = 30f
        view.elevation = 0f
        view.setCardBackgroundColor(service.primaryColor)
    }

    @JvmStatic
    @BindingAdapter("dateFormat")
    fun dateFormat(tv: TextView, str: String?) {
        try {
            val date = DateFormat.getInstance().parse(str)
            val stringBuilder = StringBuilder()
            stringBuilder.append(date.year).append(' ')
            stringBuilder.append(date.month).append(' ')
            stringBuilder.append(date.day).append(' ')
            stringBuilder.append(date.hours).append(' ')
            stringBuilder.append(date.minutes).append(' ')
            stringBuilder.append(date.seconds)

            tv.text = stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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