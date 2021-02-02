package com.open.core_theme_interface.theme;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.json.JSONObject;

public interface IColorTheme {
    boolean isLightModeStatusBar();

    int getStatusBarColor();

    int getNavigationBarColor();

    int getTextColor();

    int getSecondaryTextColor();

    int getPrimaryColor();

    int getSecondaryColor();

    int getDrawableForegroundHint();

    int getDrawableTint();

    int getWindowBackground();

    Drawable getDrawable(int resource);

    int getColor(int colorRes);

    void setTheme(Theme type);

    void setThemeChanged(LifecycleOwner owner, Observer<IColorTheme> observer);

    IColorTheme decodeJSON(JSONObject jsonObject);

    boolean needDynamicStatusColor();
}
