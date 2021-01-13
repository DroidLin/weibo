package com.open.weibo.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.open.core_base.service.ServiceFacade;
import com.open.core_theme_interface.theme.IColorTheme;

public class HBindingAdapter {
    @BindingAdapter("statefulPlaceHolder")
    public static void statefulPlaceHolder(Button view, int placeholderColor) {
        Drawable drawable = view.getBackground();
        drawable.setTint(placeholderColor);
    }

    @BindingAdapter("themeTextView")
    public static void themeTextView(TextView view, Object object) {
        IColorTheme colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme.class);
        view.setTextColor(colorThemeWrapper.getTextColor());
    }

    @BindingAdapter("themeSecondaryTextView")
    public static void themeSecondaryTextView(TextView view, Object object) {
        IColorTheme colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme.class);
        view.setTextColor(colorThemeWrapper.getSecondaryTextColor());
    }

    @BindingAdapter("themeImageView")
    public static void themeImageView(ImageView view, Object object) {
        IColorTheme colorThemeWrapper = ServiceFacade.getInstance().get(IColorTheme.class);
        view.setForeground(new ColorDrawable(colorThemeWrapper.getDrawableHint()));
    }
}
