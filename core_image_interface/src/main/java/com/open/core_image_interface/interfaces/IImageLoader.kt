package com.open.core_image_interface.interfaces

import android.widget.ImageView
import com.facebook.drawee.drawable.ScalingUtils

interface IImageLoader {
    fun load(url: String?, view: ImageView)

    fun loadRadius(url: String?, view: ImageView, radius: Float)

    fun load(url: String?, view: ImageView, scaleType: ScalingUtils.ScaleType?)
}