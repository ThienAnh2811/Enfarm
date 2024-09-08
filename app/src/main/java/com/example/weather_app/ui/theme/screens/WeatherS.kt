package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather_app.R
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.viewmodel.WeatherViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun WeatherS() {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    val dayOfWeek = currentDate.dayOfWeek
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "bd5ffc0a924060bd54f9267fa6f6ec4f"
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.Transparent)
    ) {
//        Spacer(modifier = Modifier.height(150.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp)
                .paint(
                    painterResource(id = R.drawable.farm),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                viewModel.fetchWeather("Da Nang", apiKey)
                Text(text = "Da Nang", color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${dayOfWeek}, ${formattedDate}",  color = Color.Black, fontWeight = FontWeight.Bold)
                weatherData?.let {
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .background(Color.White))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${it.main.temp.toInt()}°C ☁")
                    Column {
                        Text(text = "Humidity: ${it.main.humidity}", fontWeight = FontWeight.Bold)
                        Text(text = "Description: ${it.weather[0].description}", fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }
}