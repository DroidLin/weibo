package com.open.weibo.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.size
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent
import com.open.core_base.service.ServiceFacade
import com.open.core_image_interface.interfaces.IImage
import com.open.weibo.utils.HBindingAdapter
import kotlin.math.min

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

    fun <T> setImageUrlSrc(urls: List<T>, urlConverter: UrlConverter<T, String?>) {
        setDisplayCount(urls.size)
        val service = ServiceFacade.getInstance().get(IImage::class.java)
        var currentIndex = -1
        for (index in 0 until min(childCount, urls.size)) {
            val view = getChildAt(index)
            val url = urls[index]
            if (view is SimpleDraweeView) {
                currentIndex = index
                service.load(urlConverter.converter(url), view)
                view.visibility = View.VISIBLE
            }
        }

        for (index in currentIndex + 1 until childCount) {
            val view = getChildAt(index)
            if (view is SimpleDraweeView) {
                view.visibility = View.GONE
            }
        }
    }

    fun setDisplayCount(count: Int) {
        if (count <= 0) {
            return
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

    override fun setOnClickListener(l: OnClickListener?) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.setTag("position".hashCode(), index)
            child.setOnClickListener(l)
        }
    }
}

interface UrlConverter<Param, Result> {
    fun converter(param: Param?): Result?
}