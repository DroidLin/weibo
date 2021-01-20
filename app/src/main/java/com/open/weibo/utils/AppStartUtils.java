package com.open.weibo.utils;

import com.open.core_base.impl.ContextResolver;
import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;
import com.open.core_image.impl.ImageImpl;
import com.open.core_image_interface.interfaces.IImage;
import com.open.core_network.utils.NetworkStatusUtils;
import com.open.core_theme.impl.ColorThemeWrapper;
import com.open.core_theme_interface.theme.IColorTheme;

public class AppStartUtils {

    public static void initWithOutPermission() {
        ServiceFacade.init();
        ServiceFacade.getInstance().put(IImage.class, new ImageImpl());
        ServiceFacade.getInstance().put(IContext.class, new ContextResolver());
        ServiceFacade.getInstance().put(IColorTheme.class, new ColorThemeWrapper());

        ProfileUtils.getInstance().init();
        NetworkStatusUtils.getInstance().registerNetworkCallback(NetworkListener.getInstance());
    }
}
