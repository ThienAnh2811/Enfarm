package com.example.weather_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weather_app.admin.AdminHome
import com.example.weather_app.admin.AdminLogin
import com.example.weather_app.admin.news.AddNewsScreen
import com.example.weather_app.admin.news.NewsList
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
import com.example.weather_app.viewmodel.NewsViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun navGraph(navController: NavHostController) {
    // Use viewModel() to obtain the ViewModel instance
    val newsViewModel: NewsViewModel = viewModel()

    // Observe LiveData as Compose state
    val newsList by newsViewModel.allNews.observeAsState(emptyList())

    NavHost(navController = navController, startDestination = Screens.Login.screens) {
        composable(Screens.ManageNews.screens) {
            NewsList(navController)
        }
        composable(Screens.AdminLogin.screens) {
            AdminLogin(navController = navController)
        }
        composable(Screens.Data.screens) {
            Data()
        }
        composable(Screens.Weather.screens) {
            WeatherS()
        }
        composable(Screens.Diseases.screens) {
            Diseases()
        }
        composable(Screens.News.screens) {
            News()
        }
        composable(Screens.Knowledge.screens) {
            Knowledge()
        }
        composable(Screens.Login.screens) {
            Login(navController)
        }
        composable(Screens.SignUp.screens) {
            Signup(navController)
        }
        composable(Screens.AddNews.screens) {
            AddNewsScreen(navController, viewModel = newsViewModel)
        }
        composable(
            Screens.AdminHome.screens,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            AdminHome(navController, email!!)
        }
        composable(
            Screens.Otp.screens,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            val password = navBackStackEntry.arguments?.getString("password")
            OTP(navController = navController, email = email!!, password = password!!)
        }
        composable(
            Screens.Home.screens,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            Home(navController = navController, email = email!!)
        }
    }
}
