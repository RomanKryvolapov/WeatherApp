package com.example.weatherapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

  private const val BASE_URL = "https://api.open-meteo.com/"

  private var retrofit: Retrofit? = null

  fun getInstance(): Retrofit {
    if (retrofit == null) {
      retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpHelper.getInstance())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    return retrofit!!
  }
}