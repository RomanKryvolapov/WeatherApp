package com.example.weatherapp.models.api

import com.google.gson.annotations.SerializedName

data class WeatherApiUnitModel(
  @SerializedName("time") val time: List<String>,
  @SerializedName("temperature_2m") val temperature: List<String>,
  @SerializedName("weathercode") val weatherCode: List<String>,
  @SerializedName("windspeed_10m") val windSpeed: List<String>,
)