package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ListItemWeatherBinding
import com.example.weatherapp.models.app.WeatherModel

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.WeatherHolder>(DiffCallback()) {

  override fun getItemCount() = currentList.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
    return WeatherHolder(ListItemWeatherBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
    holder.bind(currentList[position])
  }

  inner class WeatherHolder(
    private val binding: ListItemWeatherBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: WeatherModel) {
      binding.textViewTime.text = binding.root.context.getString(
        R.string.time_text,
        model.time
      )
      binding.textViewTemperature.text = binding.root.context.getString(
        R.string.time_temperature,
        model.temperature
      )
      binding.textViewWeatherCode.text = binding.root.context.getString(
        R.string.time_weather_code,
        model.weatherCode
      )
      binding.textViewWindSpeed.text = binding.root.context.getString(
        R.string.time_wind_speed,
        model.weatherCode
      )
    }
  }

  private class DiffCallback : DiffUtil.ItemCallback<WeatherModel>() {

    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
      return oldItem == newItem
    }
  }


}