package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
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
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.ui.theme.WeatherCard
import com.example.weather_app.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(navController: NavHostController){
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    val dayOfWeek = currentDate.dayOfWeek
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
            .background(color = Color.Transparent),
//            .clip(RoundedCornerShape(20.dp))
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            viewModel.fetchWeather("Da Nang", apiKey)
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(BlueJC),
                horizontalArrangement = Arrangement.SpaceBetween){
                weatherData?.let {
                    Text(text = "  ${it.main.temp.toInt()}°C ☁︎⋅",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }

                Column(
                    modifier = Modifier.height(37.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = "DA NANG")
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = "${dayOfWeek}, ${formattedDate}")
                }
            }
//            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.torako),
                    contentDescription = "torako",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(bottom = 88.dp)
                 )
//            }

//            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                WeatherCard(label = "Diseases", icon = Icons.Default.WarningAmber, onClick = {
                    navController.navigate(Screens.Diseases.screens)
                })
                WeatherCard(label = "Weather", icon = Icons.Default.Cloud, onClick = {
                    navController.navigate(Screens.Weather.screens)
                })
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                WeatherCard(label = "Knowledge", icon = Icons.Default.Book, onClick = {
                    navController.navigate(Screens.Knowledge.screens)
                })
                WeatherCard(label = "News", icon = Icons.Default.Newspaper, onClick = {
                    navController.navigate(Screens.News.screens)
                })
            }
//            }

        }

    }
}