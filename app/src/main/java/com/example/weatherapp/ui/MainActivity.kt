package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.app.ViewState
import com.example.weatherapp.models.app.WeatherModel
import com.example.weatherapp.models.common.City
import com.example.weatherapp.utils.enableChangeAnimations
import com.example.weatherapp.utils.launch
import com.example.weatherapp.utils.setTextResource
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: WeatherAdapter
  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewModel.setupCityPosition(this)
    setupView()
    setupControls()
    subscribeToViewModel()
    getWeather()
  }

  private fun setupView() {
    adapter = WeatherAdapter()
    binding.recyclerView.adapter = adapter
    binding.recyclerView.enableChangeAnimations(false)
  }

  private fun setupControls() {
    binding.buttonReload.setOnClickListener {
      getWeather()
    }
    binding.swipeRefreshLayout.setOnRefreshListener {
      getWeather()
    }
    binding.spinnerCity.onItemSelectedListener = this
  }

  private fun getWeather() {
    viewModel.updateWeather(
      lifecycleScope = lifecycleScope,
      isSilent = adapter.itemCount > 0
    )
  }

  private fun subscribeToViewModel() {
    viewModel.viewStateFlow.onEach(::handleViewState).launch(lifecycleScope)
    viewModel.citiesStateFlow.onEach(::handleCities).launch(lifecycleScope)
    viewModel.cityPositionStateFlow.onEach(::handleCityPosition).launch(lifecycleScope)
  }

  private fun handleViewState(viewState: ViewState<List<WeatherModel>>) {
    when (viewState) {
      is ViewState.Error -> setErrorState()
      is ViewState.Loading -> setLoadingState()
      is ViewState.Success -> setSuccessState(viewState.data!!)
    }
  }

  private fun handleCities(cities: List<City>) {
    binding.spinnerCity.adapter = ArrayAdapter(
      this,
      android.R.layout.simple_spinner_item,
      cities
    )
  }

  private fun handleCityPosition(position: Int?) {
    if (position == null) return
    binding.spinnerCity.onItemSelectedListener = null
    binding.spinnerCity.setSelection(position)
    binding.spinnerCity.onItemSelectedListener = this
  }

  private fun setErrorState() {
    binding.viewState.isVisible = true
    binding.textViewState.isVisible = true
    binding.textViewState.setTextResource(R.string.error_text)
    binding.buttonReload.isVisible = true
    binding.swipeRefreshLayout.isRefreshing = false
  }

  private fun setLoadingState() {
    binding.viewState.isVisible = true
    binding.textViewState.isVisible = true
    binding.textViewState.setTextResource(R.string.loading_text)
    binding.buttonReload.isVisible = false
    binding.swipeRefreshLayout.isRefreshing = false
  }

  private fun setSuccessState(data: List<WeatherModel>) {
    binding.viewState.isVisible = false
    binding.textViewState.isVisible = false
    binding.buttonReload.isVisible = false
    binding.swipeRefreshLayout.isRefreshing = false
    adapter.submitList(data)
  }

  override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    viewModel.onCitySelectorPositionChanged(
      isSilent = adapter.itemCount > 0,
      citySelectorPosition = position,
      context = this,
      lifecycleScope = lifecycleScope,
    )
  }

  override fun onNothingSelected(parent: AdapterView<*>?) {
    // nothing
  }
}