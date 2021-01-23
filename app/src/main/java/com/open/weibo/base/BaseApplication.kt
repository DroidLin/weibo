package com.open.weibo.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.open.weibo.utils.AppStartUtils
import com.open.weibo.utils.LoginUtils

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppStartUtils.initWithOutPermission()

        Fresco.initialize(this)
        LoginUtils.getInstance().initSdk()

    }
}