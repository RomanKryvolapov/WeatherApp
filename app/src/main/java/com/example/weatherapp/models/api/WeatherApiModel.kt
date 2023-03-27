package com.example.weatherapp.models.api

import com.google.gson.annotations.SerializedName

data class WeatherApiModel(
  @SerializedName("hourly") val hourly: WeatherApiUnitModel,
)