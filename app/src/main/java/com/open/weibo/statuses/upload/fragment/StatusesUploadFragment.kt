package com.open.weibo.statuses.upload.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.open.core_base.fragment.CommonBindingFragment
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil
import com.open.core_theme_interface.theme.IColorTheme
import com.open.weibo.R
import com.open.weibo.databinding.FragmentStatusesUploadBinding
import com.open.weibo.utils.HJumpUtils
import com.open.weibo.utils.PermissionUtils
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.vm.StatusesUploadViewModel

class StatusesUploadFragment : CommonBindingFragment<FragmentStatusesUploadBinding>(),
    View.OnClickListener, Observer<IColorTheme> {

    private val vm by lazy {
        ViewModelProviders.of(requireActivity()).get(StatusesUploadViewModel::class.java)
    }

    override fun needForceQuit(): Boolean {
        val userProfile = ProfileUtils.getInstance().userProfile
        return userProfile == null
    }

    override fun initialBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStatusesUploadBinding = FragmentStatusesUploadBinding.inflate(inflater)

    override suspend fun initViews() {
        requireBinding().vm = vm
        requireBinding().clickListener = this
        requireBinding().userProfile = ProfileUtils.getInstance().userProfile

        val colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme::class.java)
        colorThemeWrapper.setThemeChanged(this, this)
        val rootView = requireBinding().topActionbar.rootView
        rootView.setBackgroundColor(colorThemeWrapper.primaryColor)
        rootView.setPadding(
            rootView.paddingLeft,
            rootView.paddingTop + StatusBarUtil.getStatusBarHeight(requireContext()),
            rootView.paddingRight,
            rootView.paddingBottom
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x3) {
            if (resultCode == Activity.RESULT_OK) {
                decodeActivityResult(data)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                activity?.finish()
            }
            R.id.addPic -> {
                if (PermissionUtils.checkPicturePermission(requireActivity(), 0x3)) {
                    HJumpUtils.JumpToPicturePick(this)
                }
            }
        }
    }

    private fun decodeActivityResult(data: Intent?) {
        val uriData = data?.data ?: return
        val resolver = context?.contentResolver ?: return
        val cursor = resolver.query(uriData, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
                val size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE))
                print("path = $path, size = $size")
            }
            cursor.close()
        }
    }

    override fun onChanged(t: IColorTheme) {
        requireBinding().executePendingBindings()
    }
}