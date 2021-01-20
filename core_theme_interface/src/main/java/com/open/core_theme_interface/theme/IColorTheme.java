package com.open.core_theme_interface.theme;

import android.graphics.drawable.Drawable;

public interface IColorTheme {
    boolean isLightModeStatusBar();

    int getStatusBarColor();

    int getNavigationBarColor();

    int getTextColor();

    int getSecondaryTextColor();

    int getPrimaryColor();

    int getSecondaryColor();

    int getDrawableHint();

    int getWindowBackground();

    Drawable getDrawable(int resource);

    int getColor(int colorRes);

    void setTheme(Theme type);
}
