package com.open.weibo.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent
import com.open.weibo.utils.HBindingAdapter

class MutilpleDraweeView : FlexboxLayout {

    private var displayCount: Int = 9
    private val maxDisplayCount: Int = 9
    private var displayRows: Int = 3
    private val maxRows: Int = 3


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP
        justifyContent = JustifyContent.FLEX_START
        for (index in 0 until maxDisplayCount) {
            val childView = SimpleDraweeView(context)
            val params =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            childView.layoutParams = params
            HBindingAdapter.themeImageView(childView, null)
            childView.visibility = View.GONE
            addView(childView)
        }
    }

    fun setDisplayCount(count: Int) {
        if (count <= 0) {
            return
        }
        displayCount = count
        if (displayCount > maxRows) {
            displayRows = maxRows
        } else {
            displayRows = displayCount
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val padding = paddingStart + paddingEnd

        val viewWidth = (width - padding).toFloat() / displayRows
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val params = child.layoutParams
            params.height = viewWidth.toInt()
            params.width = viewWidth.toInt()
            child.layoutParams = params
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}