package com.example.weatherapp.mappers

import com.example.weatherapp.models.api.WeatherApiModel
import com.example.weatherapp.models.app.WeatherModel

class MapWeatherModel : BaseMapper<WeatherApiModel, List<WeatherModel>>() {

  override fun map(from: WeatherApiModel): List<WeatherModel> {
    return from.hourly.time.mapIndexed { index, time ->
      WeatherModel(
        time = time,
        temperature = from.hourly.temperature[index],
        weatherCode = from.hourly.weatherCode[index],
        windSpeed = from.hourly.windSpeed[index],
      )
    }
  }

}