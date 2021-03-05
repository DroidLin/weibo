package com.open.weibo.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.open.core_base.impl.ContextImpl;
import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;
import com.open.core_image.impl.ImageImpl;
import com.open.core_image.impl.ImageLoaderImpl;
import com.open.core_image_interface.interfaces.IImage;
import com.open.core_image_interface.interfaces.IImageLoader;
import com.open.core_network.utils.NetworkStatusUtils;
import com.open.core_theme.impl.ColorThemeImpl;
import com.open.core_theme_interface.theme.IColorTheme;
import com.open.core_theme_interface.theme.Theme;

import java.util.List;

public class AppStartUtils {

    public static void initWithOutPermission() {
        ServiceFacade.init();
        ServiceFacade.getInstance().put(IImage.class, new ImageImpl());
        ServiceFacade.getInstance().put(IImageLoader.class, new ImageLoaderImpl());
        ServiceFacade.getInstance().put(IContext.class, new ContextImpl());
        ServiceFacade.getInstance().put(IColorTheme.class, new ColorThemeImpl());

        ProfileUtils.getInstance().init();
        EmojiUtils.getInstance().init();
        NetworkStatusUtils.getInstance().registerNetworkCallback(NetworkListener.getInstance());

        //colorTheme
        IColorTheme colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme.class);
        colorThemeWrapper.setTheme(Theme.valueOf(HPreferenceUtils.getThemeType()));
    }

    public static void stopApplication(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : appProcessInfos) {
            if (pid != info.pid) {
                android.os.Process.killProcess(info.pid);
            }
        }
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
