package com.open.core_network.impl;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GsonAdapter {

    private static GsonAdapter mInstance = null;

    public static GsonAdapter getInstance() {
        if (mInstance == null) {
            synchronized (GsonAdapter.class) {
                if (mInstance == null) {
                    mInstance = new GsonAdapter();
                }
            }
        }
        return mInstance;
    }

    private final Gson gson = new Gson();

    public <T> T parseJson(JSONObject jsonObject, Class<T> clazz) {
        try {
            if(jsonObject != null && !jsonObject.equals(JSONObject.NULL)){
                return parseString(jsonObject.toString(), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parseJson(JSONObject jsonObject, Type type) {
        try {
            if(jsonObject != null && !jsonObject.equals(JSONObject.NULL)){
                return parseString(jsonObject.toString(), type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parseString(String str, Class<T> clazz) {
        try {
            return gson.fromJson(str, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parseString(String str, Type type) {
        try {
            return gson.fromJson(str, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
