package com.example.weather_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.model.Screens
import com.example.weather_app.navigation.navGraph
import com.example.weather_app.ui.theme.Weather_AppTheme
import com.example.weather_app.viewmodel.LoadingScreen
import com.example.weather_app.viewmodel.LoginViewModel
import com.google.firebase.FirebaseApp
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        FirebaseApp.initializeApp(this)

        setContent {
            val loginViewModel: LoginViewModel = viewModel()
            val isLoading by loginViewModel.isLoading.observeAsState(true)
            val isLoggedIn by loginViewModel.isLoggedIn.observeAsState(false)

            LaunchedEffect(Unit) {
                Log.d("MainActivity", "Calling checkLoginStatus")
                loginViewModel.checkLoginStatus(applicationContext)
            }

            Weather_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoading) {
                        Log.d("MainActivity", "Loading screen is displayed")
                        LoadingScreen()
                    } else {
                        Log.d("MainActivity", "Loading complete, navigating...")
                        val navController = rememberNavController()

                        if (isLoggedIn) {
                            LaunchedEffect(Unit) {
                                Log.d("MainActivity", "Navigating to Home")
                                navController.navigate(Screens.Home.screens) {
                                    popUpTo(0)
                                }
                            }
                        } else {
                            LaunchedEffect(Unit) {
                                Log.d("MainActivity", "Navigating to Login")
                                navController.navigate(Screens.Login.screens) {
                                    popUpTo(0)
                                }
                            }
                        }

                        navGraph(navController = navController)
                    }
                }
            }
        }
    }
}




