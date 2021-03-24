package com.open.weibo.utils;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class DateFormatter {

    public static final String DEFAULT_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DAILY_RECOMMENDED_TIME_FORMAT_PATTERN = "yyyy-MM-dd";

    private static DateFormatter mInstance = null;

    @NonNull
    public static DateFormatter getInstance() {
        if (mInstance == null) {
            synchronized (DateFormatter.class) {
                if (mInstance == null) {
                    mInstance = new DateFormatter();
                }
            }
        }
        return mInstance;
    }

    public static void releaseInstance() {
        if(mInstance != null){
            mInstance.release();
            mInstance = null;
        }
    }

    private final ConcurrentHashMap<String, SimpleDateFormat> timeFormatMap = new ConcurrentHashMap<>();

    public String formatTime(String pattern, long time) {
        SimpleDateFormat dateFormat = getDateFormat(pattern);
        return dateFormat.format(time);
    }

    private SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = timeFormatMap.get(pattern);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
            timeFormatMap.put(pattern, dateFormat);
        }
        return dateFormat;
    }

    public void release() {
        timeFormatMap.clear();
    }
}
