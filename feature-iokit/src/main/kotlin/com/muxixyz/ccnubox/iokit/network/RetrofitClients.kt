package com.muxixyz.ccnubox.iokit.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClients {

    private val connectionTimeOutInSeconds = 15L
    private val readTimeOutInSeconds = 15L

    val authClient by lazy<Retrofit> {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                connectTimeout(connectionTimeOutInSeconds, TimeUnit.SECONDS)
                readTimeout(readTimeOutInSeconds, TimeUnit.SECONDS)
//                certificatePinner()
//                addInterceptor()
//                if(isDebug()) {
//                    addInterceptor()
//                    addNetworkInterceptor()
//                }
            }
            .build()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("")
            .client(okHttpClient)
            .build()
    }

    val generalClient by lazy<Retrofit> {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                connectTimeout(connectionTimeOutInSeconds, TimeUnit.SECONDS)
                readTimeout(readTimeOutInSeconds, TimeUnit.SECONDS)
//                certificatePinner()
//                addInterceptor()
//                if(isDebug()) {
//                    addInterceptor()
//                    addNetworkInterceptor()
//                }
            }
            .build()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://baidu.com/")
            .client(okHttpClient)
            .build()
    }

}