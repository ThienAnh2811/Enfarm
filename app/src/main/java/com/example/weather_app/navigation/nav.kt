package com.example.weather_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.screens.Data
import com.example.weather_app.ui.theme.screens.Diseases
import com.example.weather_app.ui.theme.screens.Home
import com.example.weather_app.ui.theme.screens.Knowledge
import com.example.weather_app.ui.theme.screens.Login
import com.example.weather_app.ui.theme.screens.News
import com.example.weather_app.ui.theme.screens.OTP
import com.example.weather_app.ui.theme.screens.Signup
import com.example.weather_app.ui.theme.screens.WeatherS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun navGraph(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screens.SignUp.screens)
    {
        composable(Screens.Home.screens) { Home(navController) }
        composable(Screens.Data.screens) { Data() }
        composable(Screens.Weather.screens) { WeatherS() }
        composable(Screens.Diseases.screens) { Diseases() }
        composable(Screens.News.screens) { News() }
        composable(Screens.Knowledge.screens) { Knowledge() }
        composable(Screens.Login.screens) { Login(navController)}
        composable(Screens.SignUp.screens) { Signup(navController) }
        composable(Screens.Otp.screens,
            arguments = listOf(
                navArgument("email"){type = NavType.StringType},
                navArgument("password"){type = NavType.StringType}
            )
        ){
            navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            val password = navBackStackEntry.arguments?.getString("password")
            OTP(navController = navController, email = email!!, password = password!!)
        }
    }
}
