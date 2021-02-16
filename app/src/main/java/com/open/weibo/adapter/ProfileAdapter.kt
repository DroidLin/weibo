package com.open.weibo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.open.core_base.adapter.CommonViewHolder
import com.open.core_base.service.ServiceFacade
import com.open.core_theme_interface.theme.IColorTheme
import com.open.core_theme_interface.theme.Theme
import com.open.weibo.R
import com.open.weibo.bean.User
import com.open.weibo.databinding.ItemProfileHeaderBinding
import com.open.weibo.databinding.ItemThemeSelectBinding
import com.open.weibo.databinding.LayoutEmptyBinding
import com.open.weibo.login.activity.AuthorizationBindingActivity
import com.open.weibo.utils.HPreferenceUtils
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.utils.ToastHelper
import kotlinx.android.synthetic.main.item_theme_select.view.*

class ProfileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val EMPTY = -1
        const val PROFILE_ANCHOR_HEADER = 0
        const val THEMES = 1
        const val SETTING = 2

        val profileArray = arrayOf(
            PROFILE_ANCHOR_HEADER,
            THEMES,
            SETTING
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PROFILE_ANCHOR_HEADER -> {
                val binding = ItemProfileHeaderBinding.inflate(inflater, parent, false)
                ProfileViewHolder(binding)
            }
            THEMES -> {
                val binding = ItemThemeSelectBinding.inflate(inflater, parent, false)
                ProfileViewHolder(binding)
            }
            else -> {
                val binding = LayoutEmptyBinding.inflate(inflater, parent, false)
                ProfileViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return profileArray[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommonViewHolder<*, *>) {
            holder.bind(null)
        }
    }

    override fun getItemCount(): Int = profileArray.size
}

private class ProfileViewHolder(binding: ViewDataBinding) :
    CommonViewHolder<ViewDataBinding, User>(binding) {
    override fun bind(item: User?) {
        notifyPendingBindings()
        when (binding) {
            is ItemProfileHeaderBinding -> {
                binding.user = ProfileUtils.getInstance().userProfile
                binding.header.setOnClickListener(this)
            }
            is ItemThemeSelectBinding -> {
                binding.themeBlack.setOnClickListener(this)
                binding.themeWhite.setOnClickListener(this)
                binding.themePink.setOnClickListener(this)
                binding.themeCustom.setOnClickListener(this)
            }
        }
    }

    override fun onClick(v: View) {
        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        var isThemeClicked = false
        when (v.id) {
            R.id.header -> {
                AuthorizationBindingActivity.launch(v.context)
            }
            R.id.theme_black -> {
                isThemeClicked = true
                colorThemeWrapper.setTheme(Theme.black)
                HPreferenceUtils.saveThemeType(Theme.black.name)
            }
            R.id.theme_white -> {
                isThemeClicked = true
                colorThemeWrapper.setTheme(Theme.white)
                HPreferenceUtils.saveThemeType(Theme.white.name)
            }
            R.id.theme_pink -> {
                isThemeClicked = true
                colorThemeWrapper.setTheme(Theme.pink)
                HPreferenceUtils.saveThemeType(Theme.pink.name)
            }
            R.id.theme_custom -> {
                /*isThemeClicked = true
                colorThemeWrapper.setTheme(Theme.custom)
                HPreferenceUtils.saveThemeType(Theme.custom.name)*/
            }
        }
        if (isThemeClicked) {
            ToastHelper.showToast("重启应用后生效")
        }
    }

}