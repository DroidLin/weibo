package com.open.core_base.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.open.core_base.coroutine.launch

abstract class CommonFragment : Fragment() {

    private var firstIn = true

    private var layoutResLoaded: Boolean = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            globalBroadcast(intent)
        }
    }

    protected open fun getLayoutId(): Int = 0

    protected open fun needForceQuit(): Boolean = false

    protected open fun getRootView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needForceQuit()) {
            activity?.finish()
            return
        }
        val intentFilter = IntentFilter()
        initIntentFilter(intentFilter)
        activity?.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            view = inflater.inflate(layoutId, container, false)
            layoutResLoaded = true
        }
        if (!layoutResLoaded) {
            view = getRootView(inflater, container, savedInstanceState)
            layoutResLoaded = true
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        if (firstIn) {
            launch {
                initViews()
                loadData()
            }
            firstIn = false
        }
    }

    protected open fun initIntentFilter(filter: IntentFilter) {}

    protected open fun globalBroadcast(intent: Intent?) {}

    protected open suspend fun initViews() {}

    protected open suspend fun loadData() {}

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}