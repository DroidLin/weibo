package com.open.weibo.adapter

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
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.ItemHomelineBinding
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
    CommonViewHolder<ItemHomelineBinding, Statuses>(binding) {
    override fun bind(item: Statuses?) {
        binding.statuses = item

        val urls = item?.pic_urls ?: return
        val displayViews = binding.root.findViewById<ViewGroup>(R.id.icon_container) ?: return
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
}