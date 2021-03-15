package com.open.weibo.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.DiffUtil
import com.facebook.drawee.view.SimpleDraweeView
import com.open.core_base.adapter.CommonPagingAdapter
import com.open.core_base.adapter.CommonViewHolder
import com.open.core_base.service.ServiceFacade
import com.open.core_image_interface.interfaces.IImage
import com.open.weibo.R
import com.open.weibo.bean.PicUrl
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.ItemHomelineBinding
import com.open.weibo.databinding.LayoutHomelineRetweetBinding
import com.open.weibo.main.activity.PicActivity
import com.open.weibo.statuses.detail.activity.StatusesDetailActivity
import com.open.weibo.view.MutilpleDraweeView
import com.open.weibo.view.UrlConverter
import kotlinx.android.synthetic.main.item_homeline.view.*
import kotlin.math.min

class HomelinePagingListAdapter(diffUtil: DiffUtil.ItemCallback<Statuses>) :
    CommonPagingAdapter<Statuses, CommonViewHolder<*, Statuses>>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<*, Statuses> =
        HomelineViewHolder(
            ItemHomelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}

class HomelineViewHolder(binding: ItemHomelineBinding) :
    CommonViewHolder<ItemHomelineBinding, Statuses>(binding), UrlConverter<PicUrl, String?> {

    private var statuses: Statuses? = null
    private var retweetBinding: LayoutHomelineRetweetBinding? = null

    override fun bind(item: Statuses?) {
        this.statuses = item
        binding.statuses = item
        binding.clickListener = this
        retweetBinding?.invalidateAll()
        notifyPendingBindings()

        val urls = item?.pic_urls ?: return
        val displayViews = binding.iconContainer
        displayViews.setImageUrlSrc(urls, this)

        val retweetItem = item.retweeted_status
        val root = binding.root.container
        if (root is ViewGroup && retweetBinding == null) {
            retweetBinding = LayoutHomelineRetweetBinding.inflate(
                LayoutInflater.from(itemView.context),
                root,
                false
            )
            root.addView(retweetBinding?.root, root.childCount - 1)
        }
        if (retweetItem == null) {
            retweetBinding?.retweetContainer?.visibility = View.GONE
            return
        }
        retweetBinding?.retweetContainer?.visibility = View.VISIBLE
        retweetBinding?.statuses = retweetItem
        val retweetDisplayViews = retweetBinding?.retweetIconContainer ?: return
        val retweetUrls = retweetItem.pic_urls ?: return
        retweetDisplayViews.setImageUrlSrc(retweetUrls, this)
    }

    private fun mutilpleImageBinding(urls: List<PicUrl>, displayViews: MutilpleDraweeView) {
        displayViews.setDisplayCount(urls.size)
        val service = ServiceFacade.getInstance().get(IImage::class.java)
        var currentIndex = -1
        for (index in 0 until min(displayViews.size, urls.size)) {
            val view = displayViews[index]
            val url = urls[index]
            if (view is SimpleDraweeView) {
                currentIndex = index
                service.load(url.thumbToMidPic(), view)
                view.visibility = View.VISIBLE
            }
        }

        for (index in currentIndex + 1 until displayViews.size) {
            val view = displayViews[index]
            if (view is SimpleDraweeView) {
                view.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            View.NO_ID -> {
                val position = v.getTag("position".hashCode()) as Int? ?: return
                val statuses = binding.statuses ?: return
                PicActivity.launch(
                    v.context,
                    "position",
                    position,
                    "picUrls",
                    statuses.pic_urls?.toTypedArray()
                )
            }
            R.id.container -> {
                StatusesDetailActivity.launch(v.context, statuses)
            }
        }
    }

    override fun converter(param: PicUrl?): String? {
        return param?.thumbToMidPic()
    }
}