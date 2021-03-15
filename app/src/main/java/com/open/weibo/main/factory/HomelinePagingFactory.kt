package com.open.weibo.main.factory

import androidx.paging.DataSource
import com.open.core_network.impl.HRetrofit
import com.open.weibo.base.BasePositionalDataSource
import com.open.weibo.bean.Statuses
import com.open.weibo.utils.HNetworkAgent
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.main.vm.HomeLineApi
import kotlinx.coroutines.CoroutineScope

class HomelinePagingFactory(private val isLocalCache: Boolean) :
    DataSource.Factory<Int, Statuses>() {
    private var dataSource: HomelineDataSource? = null

    override fun create(): DataSource<Int, Statuses> {
        if (dataSource == null || dataSource!!.isInvalid) {
            dataSource = HomelineDataSource(isLocalCache)
        }
        return dataSource!!
    }
}

class HomelineDataSource(private val isLocalCache: Boolean) : BasePositionalDataSource<Statuses>() {

    private val api by lazy { HRetrofit.getInstance().retrofit.create(HomeLineApi::class.java) }

    override suspend fun loadInit(
        coroutineScope: CoroutineScope,
        params: LoadInitialParams,
        callback: LoadInitialCallback<Statuses>
    ) {
        val profile = ProfileUtils.getInstance().profile
        if (profile != null) {
            try {
                val result = HNetworkAgent.fetchHomeLineStatuses(
                    isLocalCache,
                    "access_token", profile.token,
                    "page", 1,
                    "count", params.pageSize
                )?: return
                callback.onResult(result, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun loadRanges(
        coroutineScope: CoroutineScope,
        params: LoadRangeParams,
        callback: LoadRangeCallback<Statuses>
    ) {
        val profile = ProfileUtils.getInstance().profile
        if (profile != null) {
            try {
                val result = HNetworkAgent.fetchHomeLineStatuses(
                    isLocalCache,
                    "access_token", profile.token,
                    "page", (params.startPosition / params.loadSize) + 1,
                    "count", params.loadSize
                )?: return
                callback.onResult(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}