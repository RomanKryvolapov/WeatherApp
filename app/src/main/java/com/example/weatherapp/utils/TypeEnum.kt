package com.example.weatherapp.utils

interface TypeEnum {
  val type: String
}

inline fun <reified T : Enum<T>> getEnumTypeValue(type: String): T? {
  val values = enumValues<T>()
  return values.firstOrNull {
    it is TypeEnum && (it as TypeEnum).type.equals(type, true)
  }
}
