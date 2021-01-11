package com.open.core_image.impl

import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_image.R
import com.open.core_image_interface.interfaces.IImage

class ImageImpl : IImage {
    private val builder by lazy {
        GenericDraweeHierarchyBuilder(
            ServiceFacade.getInstance().get(
                IContext::class.java
            ).context.resources
        )
            .setFadeDuration(300)
            .setFailureImage(R.drawable.ic_pic_fail, ScalingUtils.ScaleType.CENTER_INSIDE)
            .setPlaceholderImage(R.color.gray_light)
    }

    private val hierarchy by lazy {
        builder.build()
    }

    override fun load(url: String, view: SimpleDraweeView) {
        view.hierarchy = hierarchy
        view.setImageURI(url)
    }
}