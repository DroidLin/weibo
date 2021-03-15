package com.open.weibo.statuses.detail.factory

import androidx.paging.DataSource
import com.open.core_network.impl.HRetrofit
import com.open.weibo.base.BasePositionalDataSource
import com.open.weibo.bean.Comment
import com.open.weibo.utils.ProfileUtils
import com.open.weibo.statuses.detail.vm.CommentUpdateApi
import kotlinx.coroutines.CoroutineScope


class CommentPagingFactory(private var id: Long) : DataSource.Factory<Int, Comment>() {
    private var dataSource: CommentPagingDataSource? = null

    fun setId(id: Long) {
        this.id = id
    }

    fun setFilterSource(type: Int) {
        dataSource?.setFilterSource(type)
    }

    fun invalidate() {
        dataSource?.invalidate()
    }

    override fun create(): DataSource<Int, Comment> {
        if (dataSource == null || dataSource!!.isInvalid) {
            dataSource = CommentPagingDataSource(id)
        }
        return dataSource!!
    }
}

class CommentPagingDataSource(private val id: Long) : BasePositionalDataSource<Comment>() {
    private val api by lazy { HRetrofit.getInstance().retrofit.create(CommentUpdateApi::class.java) }
    private var filterType: Int = 0
    private var page: Int = 1

    fun setFilterSource(type: Int) {
        this.filterType = type
    }

    override suspend fun loadInit(
        coroutineScope: CoroutineScope,
        params: LoadInitialParams,
        callback: LoadInitialCallback<Comment>
    ) {
        if (id == -1L) {
            return
        }

        val token = ProfileUtils.getInstance().profile?.token ?: return
        val result = try {
            api.getCommentListByStatusesId(token, id, params.pageSize, page, filterType)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }?.data?.comments ?: return

        callback.onResult(result, 0)
    }

    override suspend fun loadRanges(
        coroutineScope: CoroutineScope,
        params: LoadRangeParams,
        callback: LoadRangeCallback<Comment>
    ) {
        if (id == -1L) {
            return
        }

        val token = ProfileUtils.getInstance().profile?.token ?: return
        val result = try {
            api.getCommentListByStatusesId(token, id, params.loadSize, (++page), filterType)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }?.data?.comments ?: return

        callback.onResult(result)
    }

}