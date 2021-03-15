package com.open.weibo.statuses.detail.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.core_network.datasource.ParamResource
import com.open.core_network.datasource.SequenceDataSource
import com.open.core_network.datasource.simpleLiveData
import com.open.core_network.impl.HRetrofit
import com.open.core_network.wrapper.ApiResult
import com.open.weibo.bean.Comment
import com.open.weibo.bean.Statuses
import kotlinx.coroutines.CoroutineScope
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.net.URLEncoder

class ReplyViewModel : ViewModel() {
    var statuses: Statuses? = null
    var comment: MutableLiveData<Comment> = MutableLiveData()

    val userInput: MutableLiveData<String> = MutableLiveData()

    val replyStatusesDataSource by lazy { ReplyStatusesDataSource(viewModelScope) }
    val replyCommentDataSource by lazy { ReplyCommentDataSource(viewModelScope) }
}

class ReplyStatusesDataSource(scope: CoroutineScope) :
    SequenceDataSource<ParamResource<Map<String, Any?>, Comment>>(scope) {
    private val api by lazy { HRetrofit.getInstance().retrofit.create(ReplyStatusesApi::class.java) }

    fun replyStatuses(token: String, content: String, statuses: Statuses?) {
        val encodedContent = /*URLEncoder.encode(content, "utf-8")*/content
        val fieldMap = mapOf<String, Any?>(
            Pair("access_token", token),
            Pair("comment", encodedContent),
            Pair("id", statuses?.id)
        )

        loadSequence {
            simpleLiveData(fieldMap) {
                loadData(it) {
                    api.replyStatuses(fieldMap)
                }
            }
        }
    }
}


class ReplyCommentDataSource(scope: CoroutineScope) :
    SequenceDataSource<ParamResource<Map<String, Any?>, Comment>>(scope) {
    private val api by lazy { HRetrofit.getInstance().retrofit.create(ReplyCommentApi::class.java) }

    fun replyComment(token: String, content: String, comment: Comment?) {
        val encodedContent = /*URLEncoder.encode(content, "utf-8")*/content
        val fieldMap = mapOf<String, Any?>(
            Pair("access_token", token),
            Pair("comment", encodedContent),
            Pair("cid", comment?.id),
            Pair("id", comment?.status?.id)
        )

        loadSequence {
            simpleLiveData(fieldMap) {
                loadData(it) {
                    api.replyComment(fieldMap)
                }
            }
        }
    }
}


@JvmSuppressWildcards
interface ReplyStatusesApi {
    @FormUrlEncoded
    @POST("2/comments/create.json")
    suspend fun replyStatuses(@FieldMap map: Map<String, Any?>): ApiResult<Comment>
}

@JvmSuppressWildcards
interface ReplyCommentApi {
    @FormUrlEncoded
    @POST("2/comments/reply.json")
    suspend fun replyComment(@FieldMap map: Map<String, Any?>): ApiResult<Comment>
}