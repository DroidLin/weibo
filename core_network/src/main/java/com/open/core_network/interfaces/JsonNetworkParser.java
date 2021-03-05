package com.open.core_network.interfaces;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonNetworkParser<T> implements StringNetworkParser<T> {
    @Override
    public T parseString(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        return convert(jsonObject);
    }

    public abstract T convert(@NonNull JSONObject jsonObject);
}
