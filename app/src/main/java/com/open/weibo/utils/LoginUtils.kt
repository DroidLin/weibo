package com.open.weibo.utils

import com.sina.weibo.sdk.auth.AuthInfo

class LoginUtils {
    companion object {
        const val APP_KEY = "743626595"
        const val APP_SECRET = "36de6476a531cc74bed512ec9552e2ec"
        const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
        const val SCOPE = "email,direct_messages_write,direct_messages_read,invitation_write,follow_app_official_microblog"

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

    fun initSdk(){
    }
}