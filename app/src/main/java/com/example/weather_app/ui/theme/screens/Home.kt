package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.WeatherCard
import com.example.weather_app.viewmodel.WeatherViewModel


@Composable
fun Home(navController: NavHostController){
//    Box(modifier = Modifier.fillMaxSize()){
//        WeatherScreen()
//    }

//    NavHost(navController = navController, startDestination = Screens.Home.screens ){
//        composable(Screens.Diseases.screens){ Diseases()}
//        composable(Screens.Weather.screens){ WeatherS()}
//    }
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "bd5ffc0a924060bd54f9267fa6f6ec4f"
    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painterResource(id = R.drawable.background),
            contentScale = ContentScale.FillBounds
        )){
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
//            .clip(RoundedCornerShape(20.dp))
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            viewModel.fetchWeather("Da Nang", apiKey)
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Transparent),
                horizontalArrangement = Arrangement.Start){
                weatherData?.let {
                    Text(text = "${it.main.temp}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.torako),
                    contentDescription = "torako",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp))
            }

            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                WeatherCard(label = "Diseases", icon = Icons.Default.WarningAmber, onClick = {
                    navController.navigate(Screens.Diseases.screens)
                })
                WeatherCard(label = "Weather", icon = Icons.Default.Cloud, onClick = {
                    navController.navigate(Screens.Weather.screens)
                })
            }
//            }

        }

    }
}