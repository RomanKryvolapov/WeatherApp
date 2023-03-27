package com.example.weatherapp.utils

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import java.io.Serializable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class StringSource : Parcelable, Serializable {

  data class Text(val text: String) : StringSource()

  data class Res(@StringRes val resId: Int) : StringSource()

  fun getString(context: Context): String {
    return when (this) {
      is Text -> text
      is Res -> context.getString(resId)
    }
  }
}
