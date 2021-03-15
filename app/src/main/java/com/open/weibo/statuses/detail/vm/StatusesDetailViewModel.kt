package com.open.weibo.statuses.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.open.core_network.wrapper.ApiResult
import com.open.weibo.bean.Comment
import com.open.weibo.bean.CommentResult
import com.open.weibo.statuses.detail.factory.CommentPagingFactory
import retrofit2.http.GET
import retrofit2.http.Query

class StatusesDetailViewModel : ViewModel() {

    val pagedListLiveData: LiveData<PagedList<Comment>> by lazy {
        LivePagedListBuilder(
            commentPagingFactory,
            PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false)
                .setPrefetchDistance(1).build()
        ).build()
    }

    private val commentPagingFactory = CommentPagingFactory(-1)

    fun setStatusesId(id: Long) {
        commentPagingFactory.setId(id)
        commentPagingFactory.invalidate()
    }
}

interface CommentUpdateApi {
    @GET("2/comments/show.json")
    suspend fun getCommentListByStatusesId(
        @Query("access_token") access_token: String,
        @Query("id") id: Long,
        @Query("count") count: Int,
        @Query("page") page: Int,
        @Query("filter_by_author") filter_by_author: Int
    ): ApiResult<CommentResult>
}