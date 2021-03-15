package com.open.weibo.statuses.detail.popup

import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.open.core_network.datasource.ParamResource
import com.open.weibo.R
import com.open.weibo.bean.Comment
import com.open.weibo.databinding.LayoutReplyWindowBinding
import com.open.weibo.statuses.detail.vm.ReplyViewModel
import com.open.weibo.utils.ProfileUtils

class ReplyWindow : PopupWindow, View.OnClickListener {

    private var vm: ReplyViewModel? = null
    private var comment: Comment? = null

    private val closeSelfObserver = Observer<ParamResource<Map<String, Any?>, Comment>> {
        dismiss()
    }

    private constructor(
        binding: LayoutReplyWindowBinding,
        activity: FragmentActivity,
        width: Int,
        height: Int
    ) : this(binding.root, width, height, true) {
        vm = ViewModelProviders.of(activity).get(ReplyViewModel::class.java)
        binding.lifecycleOwner = activity
        binding.clickListener = this
        binding.vm = vm
        vm?.replyStatusesDataSource?.mediator?.observe(activity,closeSelfObserver)
        vm?.replyCommentDataSource?.mediator?.observe(activity,closeSelfObserver)
    }

    private constructor(contentView: View, width: Int, height: Int, focusable: Boolean) : super(
        contentView,
        width,
        height,
        focusable
    )

    fun setComment(comment: Comment?) {
        this.comment = comment
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.send -> {
                val profile = ProfileUtils.getInstance().profile ?: return
                val content = vm?.userInput?.value ?: return

                val comment = comment
                if (comment == null) {
                    vm?.replyStatusesDataSource?.replyStatuses(
                        profile.token!!,
                        content,
                        vm?.statuses
                    )
                } else {
                    vm?.replyCommentDataSource?.replyComment(profile.token!!, content, comment)
                }
            }
        }
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
    }

    companion object {
        @JvmStatic
        fun launch(activity: FragmentActivity): ReplyWindow {
            val binding = LayoutReplyWindowBinding.inflate(activity.layoutInflater)
            return ReplyWindow(
                binding,
                activity,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }
}