package com.open.core_image_interface.interfaces;

import com.facebook.drawee.view.SimpleDraweeView;

public interface IImage {

    void load(String url, SimpleDraweeView view);

    void loadRadius(String url, SimpleDraweeView view, float radius);

}
