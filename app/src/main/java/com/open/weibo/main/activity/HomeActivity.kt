package com.open.weibo.main.activity

import android.content.Context
import android.content.Intent

class HomeActivity : BaseHomeBindingActivity() {

    companion object {
        @JvmStatic
        fun launch(context: Context) {

            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}