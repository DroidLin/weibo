package com.open.core_image_interface.interfaces;

import androidx.annotation.NonNull;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public interface IImage {

    void load(String url, SimpleDraweeView view);

    void loadRadius(String url, SimpleDraweeView view, float radius);

    void load(String url, SimpleDraweeView view, ScalingUtils.ScaleType scaleType);
}
