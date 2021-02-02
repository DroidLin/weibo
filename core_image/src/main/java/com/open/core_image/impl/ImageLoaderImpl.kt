package com.open.core_image.impl

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.drawee.drawable.ScalingUtils
import com.open.core_image.R
import com.open.core_image.utils.GlideRoundTransform
import com.open.core_image_interface.interfaces.IImage
import com.open.core_image_interface.interfaces.IImageLoader
import com.open.core_image_interface.interfaces.ImageRequest

class ImageLoaderImpl : IImageLoader {

    override fun load(url: String?, view: ImageView) {
        val builder = build(view).load(url)
            .centerCrop()
        builder.into(view)
    }

    override fun load(url: String?, view: ImageView, scaleType: ScalingUtils.ScaleType?) {
        val builder = build(view).load(url).let {
            when (scaleType) {
                ScalingUtils.ScaleType.FIT_CENTER -> {
                    it.fitCenter()
                }
                ScalingUtils.ScaleType.CENTER -> {
                    it.centerInside()
                }
                else -> {
                    it.centerCrop()
                }
            }
            it
        }
        builder.into(view)
    }

    override fun loadRadius(url: String?, view: ImageView, radius: Float) {
        val builder = build(view)
            .load(url)
            .transform(GlideRoundTransform(radius.toInt()))
        builder.into(view)
    }

    private fun build(context: Context): RequestBuilder<Drawable> {
        return configGlide(context).defaultConfig()
    }

    private fun build(view: ImageView): RequestBuilder<Drawable> {
        return configGlide(view).defaultConfig()
    }

    private fun configGlide(context: Context): RequestManager {
        return Glide.with(context)

    }

    private fun configGlide(view: View): RequestManager {
        return Glide.with(view)
    }
}

fun RequestManager.defaultConfig(): RequestBuilder<Drawable> {
    return asDrawable()
        .error(R.drawable.ic_pic_fail)
        .placeholder(R.color.gray_light)
        .thumbnail(0.25f)
        .transition(DrawableTransitionOptions.withCrossFade(IImage.CROSSFADE_DURATION))
}