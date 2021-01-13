package com.open.weibo.utils

import android.widget.Toast
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade

class ToastHelper {
    companion object {
        fun showToast(resId: Int) {
            val resource =
                ServiceFacade.getInstance().get(IContext::class.java).applicationContext.resources
            val text = resource.getText(resId) as String
            showToast(text)
        }

        fun showToast(text: String) {
            showToast(text, Toast.LENGTH_SHORT)
        }

        fun showToast(text: String, length: Int) {
            val context = ServiceFacade.getInstance().get(IContext::class.java).applicationContext
            Toast.makeText(context, text, length).show()
        }
    }
}