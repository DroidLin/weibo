package com.open.weibo.utils;

import com.open.core_base.impl.ContextResolver;
import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;
import com.open.core_image.impl.ImageImpl;
import com.open.core_image_interface.interfaces.IImage;

public class AppStartUtils {

    public static void initWithOutPermission() {
        ServiceFacade.getInstance().put(IImage.class, new ImageImpl());
        ServiceFacade.getInstance().put(IContext.class, new ContextResolver());
    }
}
