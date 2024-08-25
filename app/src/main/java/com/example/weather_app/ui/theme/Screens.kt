package com.example.weather_app.ui.theme

sealed class Screens(val screens: String) {
    data object Home: Screens("Home")
    data object Data: Screens("Data")
}