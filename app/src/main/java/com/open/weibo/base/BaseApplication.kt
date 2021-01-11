package com.open.weibo.base

import android.app.Application
import com.open.weibo.utils.AppStartUtils

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppStartUtils.initWithOutPermission()
    }
}