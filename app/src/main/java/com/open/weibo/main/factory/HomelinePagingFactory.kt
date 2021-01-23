package com.open.weibo.main.factory

import androidx.paging.DataSource
import com.open.core_network.impl.HRetrofit
import com.open.weibo.base.BasePositionalDataSource
import com.open.weibo.bean.Statuses
import com.open.weibo.utils.HNetworkAgent
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.vm.HomeLineApi

class HomelinePagingFactory : DataSource.Factory<Int, Statuses>() {
    private var dataSource: HomelineDataSource? = null

    override fun create(): DataSource<Int, Statuses> {
        if (dataSource == null || dataSource!!.isInvalid) {
            dataSource = HomelineDataSource()
        }
        return dataSource!!
    }
}

class HomelineDataSource : BasePositionalDataSource<Statuses>() {

    private val api by lazy { HRetrofit.getInstance().retrofit.create(HomeLineApi::class.java) }

    override suspend fun loadInit(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Statuses>
    ) {
        val profile = ProfileUtils.getInstance().profile
        if (profile != null) {
            val result = HNetworkAgent.fetchHomeLineStatuses(
                "access_token", profile.token,
                "page", 1,
                "count", params.pageSize
            ) ?: return
            callback.onResult(result, 0)
        }
    }

    override suspend fun loadRanges(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Statuses>
    ) {
        val profile = ProfileUtils.getInstance().profile
        if (profile != null) {
            val result = HNetworkAgent.fetchHomeLineStatuses(
                "access_token", profile.token,
                "page", (params.startPosition / params.loadSize) + 1,
                "count", params.loadSize
            ) ?: return
            callback.onResult(result)
        }
    }

}