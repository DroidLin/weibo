package com.open.weibo.main.factory

import androidx.paging.DataSource
import com.open.core_network.impl.HRetrofit
import com.open.weibo.base.BasePositionalDataSource
import com.open.weibo.bean.Statuses
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
            val result =
                api.fetchHomeTimeLine(
                    mapOf(
                        Pair("access_token", profile.token),
                        Pair("page", 1),
                        Pair("count", params.pageSize)
                    )
                )
            val data = result.data?.statuses ?: return
            callback.onResult(data, 0)
        }
    }

    override suspend fun loadRanges(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Statuses>
    ) {
        val profile = ProfileUtils.getInstance().profile
        if (profile != null) {
            val result =
                api.fetchHomeTimeLine(
                    mapOf(
                        Pair("access_token", profile.token),
                        Pair("page", params.startPosition),
                        Pair("count", params.loadSize)
                    )
                )
            val data = result.data?.statuses ?: return
            callback.onResult(data)
        }
    }

}