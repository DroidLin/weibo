package com.open.weibo.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade

class DrawableCreator {
    companion object {
        @SuppressLint("UseCompatLoadingForDrawables")
        @JvmStatic
        fun createStatefulDrawable(placeHolder: Int): Drawable? {
            val resource =
                ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
            val placeholderDrawable = resource.getDrawable(placeHolder)
            return createStatefulDrawable(placeholderDrawable)
        }


        @JvmStatic
        fun createStatefulDrawable(placeholderDrawable: Drawable): Drawable? {
            val pressStateDrawable = placeholderDrawable.constantState?.newDrawable() ?: return null
            return createStatefulDrawable(placeholderDrawable, pressStateDrawable)
        }

        @JvmStatic
        fun createStatefulDrawable(
            placeholderDrawable: Drawable,
            pressStateDrawable: Drawable
        ): Drawable? {
            pressStateDrawable.alpha = 128
            val statefulDrawable = StateListDrawable()
            statefulDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressStateDrawable)
            statefulDrawable.addState(intArrayOf(android.R.attr.state_enabled), placeholderDrawable)
            return statefulDrawable
        }
    }
}