package com.example.weatherapp.utils

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator


/**
 * Method set text from resource in TextView
 */
fun TextView.setTextResource(resource: Int) {
  text = context.getString(resource)
}

fun RecyclerView.enableChangeAnimations(isEnable: Boolean) {
  (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = isEnable
}