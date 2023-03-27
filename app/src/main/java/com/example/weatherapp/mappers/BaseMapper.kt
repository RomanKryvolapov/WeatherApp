package com.example.weatherapp.mappers

abstract class BaseMapper<From, To> {

  abstract fun map(from: From): To

  open fun mapList(fromList: List<From>): List<To> {
    return fromList.mapTo(ArrayList(fromList.size), this::map)
  }

}