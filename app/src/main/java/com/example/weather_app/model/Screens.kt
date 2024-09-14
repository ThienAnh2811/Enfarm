package com.example.weather_app.model

import androidx.navigation.NavController
import androidx.navigation.NavHostController

sealed class Screens(val screens: String) {
    data object Home: Screens("Home")
    data object Data: Screens("Data")
    data object Diseases: Screens("Diseases")
    data object Weather: Screens("Weather")
    data object News: Screens("News")
    data object Knowledge: Screens("Knowledge")
    data object Login: Screens("Login")
    data object SignUp: Screens("SignUp")
    data object Otp: Screens("Otp/{email}/{password}"){
        fun createRoute(navController: NavHostController,
                       email: String,
                       password: String)= "Otp/$email/$password"
    }
}

