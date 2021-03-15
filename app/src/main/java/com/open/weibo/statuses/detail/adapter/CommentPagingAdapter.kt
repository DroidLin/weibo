package com.open.weibo.statuses.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.open.core_base.adapter.CommonPagingAdapter
import com.open.core_base.adapter.CommonViewHolder
import com.open.weibo.R
import com.open.weibo.bean.Comment
import com.open.weibo.databinding.ItemCommentBinding
import com.open.weibo.statuses.detail.popup.ReplyWindow
import com.open.weibo.statuses.detail.vm.ReplyViewModel

class CommentPagingAdapter(
    private val replyWindow: ReplyWindow,
    activity: FragmentActivity,
    diffUtil: DiffUtil.ItemCallback<Comment>
) :
    CommonPagingAdapter<Comment, CommonViewHolder<ItemCommentBinding, Comment>>(diffUtil) {

    private val vm by lazy {
        ViewModelProviders.of(activity).get(ReplyViewModel::class.java)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ItemCommentBinding, Comment> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater, parent, false)
        return CommentViewHolder(replyWindow, vm, binding)
    }
}

class CommentViewHolder(
    private val replyWindow: ReplyWindow,
    private val vm: ReplyViewModel,
    binding: ItemCommentBinding
) :
    CommonViewHolder<ItemCommentBinding, Comment>(binding) {

    private var comment: Comment? = null

    override fun bind(item: Comment?) {
        this.comment = item
        binding.comment = item
        binding.clickListener = this
        notifyPendingBindings()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.header -> {
                vm.comment.postValue(comment)
                replyWindow.setComment(comment)
                replyWindow.showAsDropDown(v)
            }
        }
    }

}