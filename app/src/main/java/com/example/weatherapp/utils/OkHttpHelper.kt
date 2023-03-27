package com.example.weatherapp.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpHelper {

  private var okHttpClient: OkHttpClient? = null

  fun getInstance(): OkHttpClient {
    if (okHttpClient == null) {
      okHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(1, TimeUnit.MINUTES)
        callTimeout(1, TimeUnit.MINUTES)
        readTimeout(1, TimeUnit.MINUTES)
        writeTimeout(1, TimeUnit.MINUTES)
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        cache(null)
      }.build()
    }
    return okHttpClient!!
  }
}