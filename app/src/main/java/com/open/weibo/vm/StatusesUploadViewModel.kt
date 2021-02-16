package com.open.weibo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.core_network.datasource.ParamResource
import com.open.core_network.datasource.SequenceDataSource
import com.open.core_network.impl.HRetrofit
import com.open.core_network.wrapper.ApiResult
import com.open.weibo.bean.PicBean
import com.open.weibo.bean.Statuses
import kotlinx.coroutines.CoroutineScope
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.POST
import retrofit2.http.Part

class StatusesUploadViewModel : ViewModel() {

    val statusesUploadDataSource by lazy { StatusesUploadDataSource(viewModelScope) }

    val statusesStringLiveData: MutableLiveData<String> = MutableLiveData()
}

class StatusesUploadDataSource(scope: CoroutineScope) :
    SequenceDataSource<ParamResource<Any?, Statuses>>(scope) {

    private val api by lazy { HRetrofit.getInstance().retrofit.create(StatusesUploadApi::class.java) }

}

@JvmSuppressWildcards
interface StatusesUploadApi {
    @POST("2/statuses/upload/biz.json")
    suspend fun uploadStatuses(@FieldMap map: Map<String, Any?>)

    @POST("2/statuses/update/biz.json")
    suspend fun updateStatuses(
        @Field("access_token") token: String,
        @Field("status") status: String,
        @Field("visible") visible: Int,
        @Field("is_longtext") is_longtext: Boolean
    )

    @POST("2/statuses/upload_pic/biz.json")
    suspend fun uploadPic(
        @Field("access_token") access_token: String,
        @Part("pic") part: MultipartBody.Part
    ): ApiResult<PicBean>
}