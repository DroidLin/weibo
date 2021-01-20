package com.open.weibo.utils

import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

class LoginUtils {
    companion object {
        const val APP_KEY = "743626595"
        const val APP_SECRET = "36de6476a531cc74bed512ec9552e2ec"
        const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
        const val SCOPE = "all"

        private var instance: LoginUtils? = null

        @JvmStatic
        fun getInstance(): LoginUtils {
            if (instance == null) {
                synchronized(LoginUtils::class.java) {
                    if (instance == null) {
                        instance = LoginUtils()
                    }
                }
            }
            return instance!!
        }
    }

    fun initSdk() {
        val context = ServiceFacade.getInstance().get(IContext::class.java).applicationContext
        val authInfo = AuthInfo(context, APP_KEY, REDIRECT_URL, SCOPE)
        WbSdk.install(context, authInfo)
    }
}