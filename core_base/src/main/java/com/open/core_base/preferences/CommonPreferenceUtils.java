package com.open.core_base.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonPreferenceUtils {

    public static final String settingsSp = "settings";

    private static Map<String, SharedPreferences> spMap = new ConcurrentHashMap<>();

    public static SharedPreferences getSharedPreference(String name) {
        SharedPreferences sp = spMap.get(name);
        if (sp == null) {
            sp = ServiceFacade.getInstance().get(IContext.class).getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            spMap.put(name, sp);
        }
        return sp;
    }


}
