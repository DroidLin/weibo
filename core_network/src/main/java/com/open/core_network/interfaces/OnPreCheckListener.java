package com.open.core_network.interfaces;

import org.json.JSONObject;

public interface OnPreCheckListener {
    boolean isValid(JSONObject jsonObject) throws Exception;
}
