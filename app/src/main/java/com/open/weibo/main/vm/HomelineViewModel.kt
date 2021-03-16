package com.open.weibo.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.open.core_network.datasource.ParamResource
import com.open.core_network.datasource.SequenceDataSource
import com.open.core_network.impl.HRetrofit
import com.open.core_network.wrapper.ApiResult
import com.open.weibo.bean.HomeLineResult
import com.open.weibo.bean.Statuses
import com.open.weibo.main.factory.HomelinePagingFactory
import kotlinx.coroutines.CoroutineScope
import retrofit2.http.*

class HomelineViewModel : ViewModel() {
    private val isLocalCache: MutableLiveData<Boolean> = MutableLiveData()
    private val homeLineFactory: HomelinePagingFactory by lazy { HomelinePagingFactory(isLocalCache) }

    val pagedListLiveData: LiveData<PagedList<Statuses>> = LivePagedListBuilder(
        homeLineFactory,
        PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false)
            .setPrefetchDistance(1).build()
    ).build()

    val homelineDataSource by lazy { HomeLineDataSource(viewModelScope) }

    fun invalidate(needRefresh:Boolean){
        isLocalCache.postValue(!needRefresh)
        homeLineFactory.invalidate()
    }
}

class HomeLineDataSource(scope: CoroutineScope) :
    SequenceDataSource<ParamResource<Any, List<Statuses>>>(scope) {

    val api by lazy { HRetrofit.getInstance().retrofit.create(HomeLineApi::class.java) }
}

@JvmSuppressWildcards
interface HomeLineApi {
    @GET("2/statuses/home_timeline.json")
    suspend fun fetchHomeTimeLine(@QueryMap map: Map<String, Any?>): ApiResult<HomeLineResult>
}