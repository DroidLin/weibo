package com.open.core_network.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.open.core_network.wrapper.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async


open class DataSource(coroutineScope: CoroutineScope) {

    protected val scope = coroutineScope

    suspend fun <P, R, M> loadConverter(
        param: P?,
        remote: suspend (P?) -> ApiResult<M>,
        converter: suspend (M?) -> R = { it as R }
    ): ParamResource<P, R> {
        return try {
            val response = remote(param)
            val data = response.data
            ParamResource.success(param, converter(data))
        } catch (e: Throwable) {
            e.printStackTrace()
            ParamResource.error(param, null, e)
        }
    }
}

open class SequenceDataSource<T>(coroutineScope: CoroutineScope) : DataSource(coroutineScope) {

    val mediator: MediatorLiveData<T> = MediatorLiveData()

    private var lastRequest: LiveData<T>? = null

    fun loadSequence(block: () -> LiveData<T>): LiveData<T> {
        lastRequest?.let {
            mediator.removeSource(it)
        }
        lastRequest = block.invoke()
        lastRequest?.let {
            mediator.addSource(it) { data ->
                mediator.value = data
            }
        }
        return lastRequest!!
    }

    suspend fun <P, R> loadData(
        param: P?,
        block: suspend (params: P?) -> ApiResult<R>
    ): ParamResource<P, R> {
        return loadConverter(param, block)
    }


    fun <T> loadAsync(block: suspend () -> ApiResult<T>): Deferred<T?> {
        return scope.async {
            val result = block.invoke()
            result.data
        }
    }
}

fun LiveData<ParamResource<*, *>>.isLoading(): Boolean {
    return value?.isLoading() ?: false
}

fun <P, R> simpleLiveData(
    param: P?,
    block: suspend (P?) -> ParamResource<P, R>
): LiveData<ParamResource<P, R>> =
    liveData {
        emit(ParamResource.loading<P, R>(param))
        try {
            val result = block.invoke(param)
            emit(result)
        } catch (e: Throwable) {
            e.printStackTrace()
            emit(ParamResource.error<P, R>(param, throwable = e))
        }
    }
