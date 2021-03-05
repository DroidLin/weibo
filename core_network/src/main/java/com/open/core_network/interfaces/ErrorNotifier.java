package com.open.core_network.interfaces;

import androidx.annotation.MainThread;

public interface ErrorNotifier {
    /**
     * called when error occurred , runs on main thread
     * @param throwable error message
     */
    @MainThread
    void notifyError(Throwable throwable);
}
