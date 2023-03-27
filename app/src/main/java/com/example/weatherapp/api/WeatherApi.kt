package com.example.weatherapp.api

import com.example.weatherapp.models.api.WeatherApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

  @GET("/v1/forecast")
  suspend fun getWeather(
    @Query("latitude") latitude: Float,
    @Query("longitude") longitude: Float,
    @Query("hourly") hourly: String,
  ): Response<WeatherApiModel>

}