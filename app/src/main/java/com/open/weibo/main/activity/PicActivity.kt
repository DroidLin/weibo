package com.open.weibo.main.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bm.library.PhotoView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.open.core_base.activity.BaseViewPagerActivity
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.OSUtils
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_image_interface.interfaces.IImage
import com.open.core_image_interface.interfaces.IImageLoader
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.bean.PicUrl
import com.open.weibo.view.ZoomDraweeView
import com.open.weibo.view.ZoomSimpleDraweeView
import java.io.Serializable

class PicActivity : BaseViewPagerActivity<PicUrl, PhotoView>() {

    private var picUrls: List<PicUrl>? = null

    override suspend fun initViews() {
        val `object` = intent.extras?.get("picUrls")
        if (`object` != null && `object` is Array<*>) {
            val picUrls = ArrayList<PicUrl>()
            for (index in 0 until `object`.size) {
                val item = `object`[index]
                if (item is PicUrl) {
                    picUrls.add(item)
                }
            }
            this.picUrls = picUrls
        }
        super.initViews()
    }

    override fun getCount(): Int = picUrls?.size ?: 0

    override fun getViewItem(): PhotoView {
        val imageView = PhotoView(this)
        val params = RecyclerView.LayoutParams(
            ViewPager.LayoutParams.MATCH_PARENT,
            ViewPager.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        return imageView
    }

    override fun getMetaData(position: Int): PicUrl? {
        val size = picUrls?.size ?: 0
        if (position < size) {
            return picUrls?.get(position)
        }
        return null
    }

    override fun bind(view: PhotoView, data: PicUrl) {
        view.enable()
        view.maxScale = 4f
        view.scaleType = ImageView.ScaleType.FIT_CENTER
        val service = ServiceFacade.getInstance().get(IImageLoader::class.java)
        val url = data.thumbToLarge()
        if (url != null) {
            service.load(url, view, ScalingUtils.ScaleType.FIT_CENTER)
        }
    }

    override fun currentPosition(): Int {
        val bundle = intent.extras
        return bundle?.getInt("position") ?: 0
    }

    companion object {
        @JvmStatic
        fun launch(context: Context, vararg params: Serializable?) {
            val intent = Intent(context, PicActivity::class.java)
            val bundle = Bundle()
            var index = 0
            while (index < params.size) {
                bundle.putSerializable(params[index].toString(), params[index + 1])
                index += 2
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

}