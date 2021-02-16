package com.open.weibo.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.open.core_base.permission.Permission;
import com.open.core_base.permission.PermissionsObject;

public class PermissionUtils {

    public static boolean checkPicturePermission(@NonNull Activity activity, int requestCode) {
        boolean result = Permission.checkPermissionGranted(activity, PermissionsObject.INSTANCE.getPicturePickPermission());
        if (!result) {
            Permission.accessForPermission(activity, requestCode, PermissionsObject.INSTANCE.getPicturePickPermission());
        }
        return result;
    }
}
