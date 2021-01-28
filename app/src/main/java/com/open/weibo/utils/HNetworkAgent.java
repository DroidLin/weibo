package com.open.weibo.utils;

import androidx.annotation.Nullable;

import com.open.core_network.impl.GsonAdapter;
import com.open.core_network.impl.NetworkAgent;
import com.open.weibo.bean.HomeLineResult;
import com.open.weibo.bean.Statuses;

import java.util.List;

public class HNetworkAgent {

    @Nullable
    public static List<Statuses> fetchHomeLineStatuses(Object... params) {
        return NetworkAgent.getInstance().loadApi("2/statuses/home_timeline.json")
                .setParams(params)
                .setErrorNotifier(throwable -> ToastHelper.showToast(throwable.getMessage()))
                .executeApi(jsonObject -> {
                    final HomeLineResult result = GsonAdapter.getInstance().parseJson(jsonObject, HomeLineResult.class);
                    return result.getStatuses();
                });
    }
}
