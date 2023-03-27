package com.example.weatherapp.models.app

sealed class ViewState<T>(val data: T? = null) {
  class Loading<T> : ViewState<T>()
  class Error<T> : ViewState<T>()
  class Success<T>(data: T) : ViewState<T>(data)
}