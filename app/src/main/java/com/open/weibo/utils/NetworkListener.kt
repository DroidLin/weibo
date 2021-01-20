package com.open.weibo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade


class NetworkListener : ConnectivityManager.NetworkCallback() {

    companion object {

        private var instance: NetworkListener? = null

        @JvmStatic
        fun getInstance(): NetworkListener {
            if (instance == null) {
                synchronized(NetworkListener::class.java) {
                    if (instance == null) {
                        instance = NetworkListener()
                    }
                }
            }
            return instance!!
        }
    }

    private val networkAvailableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun registerNetworkObserver(owner:LifecycleOwner, observer: Observer<Boolean>){
        networkAvailableLiveData.observe(owner, observer)
    }

    override fun onAvailable(network: Network?) {
        val connectivityManager =
            ServiceFacade.getInstance().get(IContext::class.java).context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val available = connectivityManager.getNetworkInfo(network).isConnected
        networkAvailableLiveData.postValue(available)
    }

    override fun onUnavailable() {
        networkAvailableLiveData.postValue(false)
    }
}