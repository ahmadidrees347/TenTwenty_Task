package com.ten.twenty.task.data.network

import retrofit2.Response

abstract class ApiResponseRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}