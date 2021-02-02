package com.open.weibo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import com.facebook.drawee.view.SimpleDraweeView

class ZoomSimpleDraweeView : SimpleDraweeView {

    private var scaleMatrix: Matrix? = null
    private

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )




}