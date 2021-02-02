package com.open.weibo.base

import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class BasePositionalDataSource<T : Any> : PositionalDataSource<T>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        runBlocking {
            loadInit(this, params, callback)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        runBlocking {
            loadRanges(this, params, callback)
        }
    }

    abstract suspend fun loadInit(
        coroutineScope: CoroutineScope,
        params: LoadInitialParams,
        callback: LoadInitialCallback<T>
    )

    abstract suspend fun loadRanges(
        coroutineScope: CoroutineScope,
        params: LoadRangeParams,
        callback: LoadRangeCallback<T>
    )
}