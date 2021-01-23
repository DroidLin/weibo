package com.open.core_network.impl;

public class NetworkAgent {

    private static NetworkAgent mInstance = null;

    public static NetworkAgent getInstance() {
        if (mInstance == null) {
            synchronized (NetworkAgent.class) {
                if (mInstance == null) {
                    mInstance = new NetworkAgent();
                }
            }
        }
        return mInstance;
    }

    public NetworkRequest loadApi(String url) {
        return new NetworkRequest(url);
    }
}
