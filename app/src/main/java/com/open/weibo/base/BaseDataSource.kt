package com.open.weibo.base

import androidx.paging.PositionalDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BasePositionalDataSource<T : Any>:PositionalDataSource<T>(){
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        GlobalScope.launch {
            loadInit(params, callback)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        GlobalScope.launch {
            loadRanges(params, callback)
        }
    }

    abstract suspend fun loadInit(params: LoadInitialParams, callback: LoadInitialCallback<T>)
    abstract suspend fun loadRanges(params: LoadRangeParams, callback: LoadRangeCallback<T>)
}