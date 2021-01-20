package com.open.core_base.permission

import android.Manifest

object PermissionsObject {
    /**
     * permissions that needed
     */
    public val permission: Array<String> = arrayOf(
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
        Manifest.permission.INTERNET,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.READ_PHONE_STATE
    )
}