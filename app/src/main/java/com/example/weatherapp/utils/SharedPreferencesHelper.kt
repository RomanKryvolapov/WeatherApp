package com.example.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences

object SharedPreferencesHelper {

  private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"

  private var sharedPreferences: SharedPreferences? = null

  fun getInstance(context: Context): SharedPreferences {
    if (sharedPreferences == null) {
      sharedPreferences = EncryptedSharedPreferences.create(
        "${context.packageName}.pref",
        SHARED_PREFERENCES_KEY,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
      )
    }
    return sharedPreferences!!
  }
}