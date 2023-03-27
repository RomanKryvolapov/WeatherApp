package com.example.weatherapp.ui

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.weatherapp.utils.RetrofitHelper
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.mappers.MapWeatherModel
import com.example.weatherapp.models.app.ViewState
import com.example.weatherapp.models.app.WeatherModel
import com.example.weatherapp.models.common.City
import com.example.weatherapp.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

  companion object {
    private const val CITY_POSITION_PREFS_KEY = "CITY_POSITION_PREFS_KEY"
  }

  private var updateWeatherJob: Job? = null
  private var onCitySelectorPositionChangedJob: Job? = null

  private var cityPosition: Int? = null

  private val _viewStateFlow = MutableStateFlow<ViewState<List<WeatherModel>>>(ViewState.Loading())
  val viewStateFlow: StateFlow<ViewState<List<WeatherModel>>> = _viewStateFlow

  private val _citiesStateFlow = MutableStateFlow(listOf(City.SOFIA, City.BURGAS, City.PLOVDIV))
  val citiesStateFlow: StateFlow<List<City>> = _citiesStateFlow

  private val _cityPositionStateFlow = MutableStateFlow(cityPosition)
  val cityPositionStateFlow: StateFlow<Int?> = _cityPositionStateFlow

  fun setupCityPosition(context: Context) {
    if (cityPosition == null || cityPositionStateFlow.value == null) {
      cityPosition = SharedPreferencesHelper.getInstance(context)
        .getInt(CITY_POSITION_PREFS_KEY, 0)
      _cityPositionStateFlow.value = cityPosition
    }
  }

  fun onCitySelectorPositionChanged(
    isSilent: Boolean,
    citySelectorPosition: Int,
    context: Context,
    lifecycleScope: LifecycleCoroutineScope,
  ) {
    onCitySelectorPositionChangedJob?.cancel()
    onCitySelectorPositionChangedJob = lifecycleScope.launch(Dispatchers.IO) {
      if (citySelectorPosition == cityPosition) return@launch
      cityPosition = citySelectorPosition
      SharedPreferencesHelper.getInstance(context).edit()
        .putInt(CITY_POSITION_PREFS_KEY, citySelectorPosition).apply()
      updateWeather(
        isSilent = isSilent,
        lifecycleScope = lifecycleScope,
      )
    }
  }

  fun updateWeather(
    isSilent: Boolean,
    lifecycleScope: LifecycleCoroutineScope,
  ) {
    updateWeatherJob?.cancel()
    updateWeatherJob = lifecycleScope.launch(Dispatchers.IO) {
      if (!isSilent) _viewStateFlow.value = ViewState.Loading()
      val weatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
      val response = weatherApi.getWeather(
        citiesStateFlow.value[cityPosition!!].latitude,
        citiesStateFlow.value[cityPosition!!].longitude,
        "temperature_2m,weathercode,windspeed_10m"
      )
      if (!response.isSuccessful) {
        _viewStateFlow.value = ViewState.Error()
        return@launch
      }
      val body = response.body()
      if (body == null) {
        _viewStateFlow.value = ViewState.Error()
        return@launch
      }
      try {
        val resultList = MapWeatherModel().map(body)
        if (resultList.isEmpty()) {
          _viewStateFlow.value = ViewState.Error()
          return@launch
        }
        _viewStateFlow.value = ViewState.Success(resultList)
      } catch (e: Exception) {
        _viewStateFlow.value = ViewState.Error()
      }
    }
  }
}