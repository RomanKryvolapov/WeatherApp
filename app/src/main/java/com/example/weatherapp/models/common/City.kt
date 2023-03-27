package com.example.weatherapp.models.common

import com.example.weatherapp.utils.TypeEnum

enum class City(
  override val type: String,
  val latitude: Float,
  val longitude: Float,
): TypeEnum {
  SOFIA("Sofia", 42.70f, 23.32f),
  PLOVDIV("Plovdiv", 42.15f, 24.75f),
  BURGAS("Burgas ", 42.51f, 27.47f),
}