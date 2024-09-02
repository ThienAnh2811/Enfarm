package com.example.weather_app.model

sealed class Screens(val screens: String) {
    data object Home: Screens("Home")
    data object Data: Screens("Data")
    data object Diseases: Screens("Diseases")
    data object Weather: Screens("Weather")
}

