package com.open.weibo.statuses.detail.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.R
import com.open.weibo.base.BaseBindingActivity
import com.open.weibo.bean.PicUrl
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.ActivityStatusesDetailBinding
import com.open.weibo.databinding.LayoutHomelineRetweetBinding
import com.open.weibo.stratagy.FloatingAnimStratagy
import com.open.weibo.utils.ToastHelper
import com.open.weibo.view.UrlConverter
import com.open.weibo.vm.StatusesDetailViewModel
import kotlinx.android.synthetic.main.item_homeline.view.*

class StatusesDetailActivity : BaseBindingActivity<ActivityStatusesDetailBinding>(),
    View.OnClickListener, AppBarLayout.OnOffsetChangedListener, UrlConverter<PicUrl, String?> {

    private val vm by lazy { ViewModelProviders.of(this).get(StatusesDetailViewModel::class.java) }
    private val floatingAnimStratagy: FloatingAnimStratagy by lazy { FloatingAnimStratagy() }
    private var statuses: Statuses? = null
    private var retweetBinding: LayoutHomelineRetweetBinding? = null

    override fun initialBinding(): ActivityStatusesDetailBinding =
        ActivityStatusesDetailBinding.inflate(layoutInflater)

    override suspend fun initViews() {
        val statuses = intent?.extras?.getSerializable("statuses") as Statuses?
        this.statuses = statuses
        if (statuses == null) {
            ToastHelper.showToast("发生未知错误! ")
            finish()
            return
        }

        floatingAnimStratagy.setTargetView(requireBinding().commonActionbar.root)

        vm.setStatusesId(statuses.id)
        requireBinding().statuses = statuses
        requireBinding().clickListener = this
        requireBinding().appbarLayout.addOnOffsetChangedListener(this)
        requireBinding().appbarLayout.setPadding(
            0,
            StatusBarUtil.getStatusBarHeight(this),
            0,
            0
        )
        requireBinding().commonActionbar.root.setPadding(
            0,
            StatusBarUtil.getStatusBarHeight(this),
            0,
            0
        )

        val service = ServiceFacade.getInstance().get(IColorTheme::class.java)
        service.setThemeChanged(this, this)

        val urls = statuses.pic_urls ?: return
        val multipleDraweeView = requireBinding().itemHomeLine.iconContainer
        multipleDraweeView.setImageUrlSrc(urls, this)

        val retweetItem = statuses.retweeted_status ?: return
        val root = requireBinding().itemHomeLine.container
        if (retweetBinding == null) {
            retweetBinding = LayoutHomelineRetweetBinding.inflate(
                LayoutInflater.from(this),
                root,
                false
            )
            root.addView(retweetBinding?.root, root.childCount - 1)
        }
        retweetBinding?.retweetContainer?.visibility = View.VISIBLE
        retweetBinding?.statuses = retweetItem
        val retweetDisplayViews = retweetBinding?.retweetIconContainer ?: return
        val retweetUrls = retweetItem.pic_urls ?: return
        retweetDisplayViews.setImageUrlSrc(retweetUrls, this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            val height = appBarLayout.height
            val scale = (height + verticalOffset) / height.toFloat()
            appBarLayout.alpha = scale
            if (scale > 0.85f) {
                floatingAnimStratagy.fadeOff()
            } else {
                floatingAnimStratagy.fadeIn()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
        }
    }

    override fun converter(param: PicUrl?): String? {
        return param?.thumbToMidPic()
    }

    override fun onChanged(t: IColorTheme) {
        configUIThemes()
        requireBinding().invalidateAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireBinding().appbarLayout.removeOnOffsetChangedListener(this)
    }

    companion object {

        @JvmStatic
        fun launch(context: Context, statuses: Statuses?) {
            if (statuses == null) {
                ToastHelper.showToast("statuses == null")
                return
            }

            val intent = Intent(context, StatusesDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("statuses", statuses)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}