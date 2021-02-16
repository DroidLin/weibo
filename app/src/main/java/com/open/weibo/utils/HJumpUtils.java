package com.open.weibo.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

public class HJumpUtils {

    public static void JumpToPicturePick(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        fragment.startActivityForResult(intent, 0x3);
    }
}
