package com.moapps.moviesnews.data.network

import com.moapps.moviesnews.utilis.Credentials.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(networkConnectionInterceptor: NetworkConnectionInterceptor) {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()

    fun <T> getAPI(API: Class<T>): T {
        return retrofit.create(API)
    }

}