package com.open.core_base.impl

import android.app.Application
import android.content.Context
import com.open.core_base.provider.ContextProvider
import com.open.core_base.interfaces.IContext

class ContextResolver : IContext {
    companion object {
        private val mContext: Context by lazy { ContextProvider.mContext!! }
    }

    override fun getContext(): Context = mContext

    override fun getApplicationContext(): Context = mContext.applicationContext

    override fun getApplication(): Application = (mContext.applicationContext as Application)

}