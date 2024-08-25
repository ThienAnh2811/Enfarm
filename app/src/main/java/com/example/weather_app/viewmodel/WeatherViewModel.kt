package com.example.weather_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.WeatherResponse
import com.example.weather_app.api.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherApi.create()

    fun fetchWeather(city: String, apiKey: String){
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(city, apiKey)
                _weatherData.value = response
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}