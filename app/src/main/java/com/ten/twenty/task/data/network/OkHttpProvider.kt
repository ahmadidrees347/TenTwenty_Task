package com.ten.twenty.task.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

object OkHttpProvider {

    val instance: OkHttpClient

    init {
        val logger = HttpLoggingInterceptor { Timber.d(it) }
        logger.level = HttpLoggingInterceptor.Level.BASIC

        instance = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "eab7ce4b39c8419a66cf7c48be6e23e5")
                .build()

            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }.addInterceptor(logger).build()
    }
}