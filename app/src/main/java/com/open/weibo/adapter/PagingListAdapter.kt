package com.open.weibo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.open.core_base.adapter.CommonPagingAdapter
import com.open.core_base.adapter.CommonViewHolder
import com.open.weibo.bean.Statuses
import com.open.weibo.databinding.ItemHomelineBinding

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
    }
}