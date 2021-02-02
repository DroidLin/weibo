package com.open.weibo.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Field;

public class ViewPager2Utils {

    public static LinearLayoutManager getLayoutManager(ViewPager2 viewPager2) {
        try {
            Field layoutManagerField = viewPager2.getClass().getDeclaredField("mLayoutManager");
            layoutManagerField.setAccessible(true);
            Object layoutManager = layoutManagerField.get(viewPager2);
            if (layoutManager instanceof LinearLayoutManager) {
                return (LinearLayoutManager) layoutManager;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
