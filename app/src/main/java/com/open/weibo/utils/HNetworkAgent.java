package com.open.weibo.utils;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.open.core_network.impl.GsonAdapter;
import com.open.core_network.impl.MoshiConverterAdapter;
import com.open.core_network.impl.NetworkAgent;
import com.open.core_network.impl.NetworkRequest;
import com.open.core_network.interfaces.JsonNetworkParser;
import com.open.weibo.bean.HomeLineResult;
import com.open.weibo.bean.Statuses;
import com.open.weibo.database.bean.Emoji;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class HNetworkAgent {

    @Nullable
    public static List<Emoji> getOfficialEmoji(boolean isLocalCache, Object... params) {
        return NetworkAgent.getInstance().loadApi("2/emotions.json")
                .setParams(params)
                .doGet()
                .setForceLocalCache(isLocalCache)
                .executeApi(string -> {
                    Type type = new TypeToken<List<Emoji>>() {
                    }.getType();
                    return MoshiConverterAdapter.getInstance().parseString(string, type);
                });
    }

    @Nullable
    public static List<Statuses> fetchHomeLineStatuses(boolean isLocalCache, Object... params) {
        return NetworkAgent.getInstance()
                .loadApi("2/statuses/home_timeline.json")
                .setParams(params)
                .doGet()
                .setErrorNotifier(throwable -> ToastHelper.showToast(throwable.getMessage(), Toast.LENGTH_LONG))
                .setForceLocalCache(isLocalCache)
                .executeApi(new JsonNetworkParser<List<Statuses>>() {
                    @Override
                    public List<Statuses> convert(@NonNull JSONObject jsonObject) {
                        final HomeLineResult result = MoshiConverterAdapter.getInstance().parseJson(jsonObject, HomeLineResult.class);
                        return result.getStatuses();
                    }
                });
    }

    @Nullable
    public static String getUserDetailProfile(Object... params) {
        return NetworkAgent.getInstance()
                .loadApi("2/users/show.json")
                .setParams(params)
                .doGet()
                .setErrorNotifier(throwable -> ToastHelper.showToast(throwable.getMessage(), Toast.LENGTH_LONG))
                .executeApi(string -> string);
    }
}
