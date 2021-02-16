package com.open.weibo.statuses.upload.activity

import android.content.Context
import android.content.Intent
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.base.BaseBindingActivity
import com.open.weibo.databinding.ActivityUploadStatusesBinding
import com.open.weibo.login.activity.AuthorizationBindingActivity
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.utils.ToastHelper

class UploadStatusesActivity : BaseBindingActivity<ActivityUploadStatusesBinding>() {
    override fun initialBinding(): ActivityUploadStatusesBinding =
        ActivityUploadStatusesBinding.inflate(layoutInflater)

    override suspend fun initViews() {
        val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
        service.setThemeChanged(this, this)
    }

    override fun onChanged(t: IColorTheme) {
        configUIThemes()
        requireBinding().invalidateAll()
    }

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            val userProfile = ProfileUtils.getInstance().userProfile
            if (userProfile == null) {
                ToastHelper.showToast("请登录后重试")
                AuthorizationBindingActivity.launch(context)
                return
            }

            context.startActivity(Intent(context, UploadStatusesActivity::class.java))
        }
    }
}