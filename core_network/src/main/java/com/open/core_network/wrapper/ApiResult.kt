package com.open.core_network.wrapper

import java.io.Serializable
import java.net.HttpURLConnection

open class ApiResult<Data>(
    var code: Int = HttpURLConnection.HTTP_OK,
    var message: String? = null,
    var httpCode: Int = HttpURLConnection.HTTP_OK,
    var data: Data? = null
) : Serializable {
    fun isSuccess(): Boolean = code in 200..299
}