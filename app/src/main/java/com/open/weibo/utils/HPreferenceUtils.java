package com.open.weibo.utils;

import androidx.annotation.Nullable;

import com.open.core_base.preferences.CommonPreferenceUtils;
import com.open.core_network.impl.GsonAdapter;
import com.open.core_network.impl.MoshiConverterAdapter;
import com.open.weibo.bean.User;

public class HPreferenceUtils {
    public static void saveDetailProfileString(String profileString) {
        CommonPreferenceUtils.getSharedPreference(
                CommonPreferenceUtils.profilesSp
        )
                .edit()
                .putString(User.spKey, profileString)
                .apply();
    }

    @Nullable
    public static User getDetailProfile() {
        String profileString = CommonPreferenceUtils.getSharedPreference(CommonPreferenceUtils.profilesSp).getString(User.spKey, null);
        return MoshiConverterAdapter.getInstance().parseString(profileString, User.class);
    }

    public static void saveThemeType(String themeName) {
        CommonPreferenceUtils.getSharedPreference(
                CommonPreferenceUtils.settingsSp
        )
                .edit()
                .putString("theme", themeName)
                .apply();
    }

    public static String getThemeType() {
        return CommonPreferenceUtils.getSharedPreference(CommonPreferenceUtils.settingsSp)
                .getString("theme", "white");
    }
}
