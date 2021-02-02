package com.open.weibo.login.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.R
import com.open.weibo.base.BaseBindingActivity
import com.open.weibo.databinding.ActivityAuthorizationBinding
import com.open.weibo.utils.*
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationBindingActivity : BaseBindingActivity<ActivityAuthorizationBinding>(),
    WbAuthListener,
    View.OnClickListener {

    private val ssoHandler: SsoHandler by lazy { SsoHandler(this) }

    override fun initialBinding(): ActivityAuthorizationBinding =
        ActivityAuthorizationBinding.inflate(LayoutInflater.from(this))

    override suspend fun initViews() {
        requireBinding().clickListener = this
        ToastHelper.showToast("此界面用于确认授权信息，点击确认进入授权页面", Toast.LENGTH_LONG)
    }

    override fun onSuccess(p0: Oauth2AccessToken) {
        ToastHelper.showToast(R.string.authorization_success)

        lifecycleScope.launchWhenCreated {
            ProfileUtils.getInstance().saveUserProfile(p0)
            val profileToken = ProfileUtils.getInstance().profile ?: return@launchWhenCreated
            withContext(Dispatchers.IO) {
                val profileString = HNetworkAgent.getUserDetailProfile(
                    "access_token",
                    profileToken.token,
                    "uid",
                    profileToken.uid
                )
                if (profileString != null) {
                    ProfileUtils.getInstance().saveUserDetail(profileString)
                }
                val intent = Intent()
                intent.action = ProjectConfig.LOGIN_GRANTED_EVENT
                sendBroadcast(intent)
                finish()
            }
        }
    }

    override fun cancel() {
        ToastHelper.showToast(R.string.authorization_cancel)
    }

    override fun onFailure(p0: WbConnectErrorMessage) {
        ToastHelper.showToast(p0.errorMessage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ssoHandler.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.authorize_cancel -> {
                finish()
            }
            R.id.authorize_ok -> {
                ssoHandler.authorize(this)
            }
        }
    }

    override fun onChanged(t: IColorTheme) {
        requireBinding().invalidateAll()
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, AuthorizationBindingActivity::class.java)
            context.startActivity(intent)
        }
    }

}