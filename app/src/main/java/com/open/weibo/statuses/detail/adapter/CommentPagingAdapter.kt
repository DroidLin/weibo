package com.open.weibo.statuses.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.open.core_base.adapter.CommonPagingAdapter
import com.open.core_base.adapter.CommonViewHolder
import com.open.weibo.bean.Comment
import com.open.weibo.databinding.ItemCommentBinding

class CommentPagingAdapter(diffUtil: DiffUtil.ItemCallback<Comment>) :
    CommonPagingAdapter<Comment, CommonViewHolder<ItemCommentBinding, Comment>>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ItemCommentBinding, Comment> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater, parent, false)
        return CommentViewHolder(binding)
    }
}

class CommentViewHolder(binding: ItemCommentBinding) :
    CommonViewHolder<ItemCommentBinding, Comment>(binding) {

    private var comment: Comment? = null

    override fun bind(item: Comment?) {
        this.comment = item
        binding.comment = item
        binding.clickListener = this
        notifyPendingBindings()
    }

    override fun onClick(v: View) {

    }

}