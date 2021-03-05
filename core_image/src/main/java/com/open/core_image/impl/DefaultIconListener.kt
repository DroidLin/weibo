package com.open.core_image.impl

import com.open.core_image_interface.interfaces.DrawableLoadListener

abstract class DefaultIconListener : DrawableLoadListener {
    override fun onLoadFailed(e: Exception?) {
    }
}