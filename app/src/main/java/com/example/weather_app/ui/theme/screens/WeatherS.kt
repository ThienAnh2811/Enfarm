package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults.shape
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
import com.example.weather_app.ui.theme.DarkBlueJC
import com.example.weather_app.ui.theme.GrayJC
import com.example.weather_app.viewmodel.WeatherViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.screens.news.NewCard
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherS(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Weather",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            WeatherPreview()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WeatherPreview(){
    val apiKey = "bd5ffc0a924060bd54f9267fa6f6ec4f"
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    val dayOfWeek = currentDate.dayOfWeek
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    Column(
        modifier = Modifier.background(GrayJC)
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text("Today",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 28.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = "DA NANG",
            color = Color.White,
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = "${dayOfWeek}, ${formattedDate}",
            color = Color.White,
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier.height(500.dp)
                .clip(RoundedCornerShape(15.dp))
                .width(400.dp)
//                .border(width = 2.dp,
//                    color = Color.DarkGray,
//                    shape = RoundedCornerShape(12.dp)
//                )
                .background(Color.Transparent),
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Image(
                    painterResource(R.drawable.weather),
                    contentDescription = null,
                    modifier = Modifier.height(150.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                viewModel.fetchWeather("Da Nang", apiKey)
                weatherData?.let {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painterResource(R.drawable.sunny),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "${it.main.temp.toInt()}°C",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 20.dp))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painterResource(R.drawable.humidity),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),

                        )
                        Text(
                            text = "${it.main.humidity}",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painterResource(R.drawable.windy),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = it.weather[0].description.replaceFirstChar { char ->
                                if (char.isLowerCase()) char.titlecase() else char.toString()
                            },
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 20.dp)
                        )

                    }
                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround,
//                ) {
//
//                    weatherData?.let {
//                        Text(text = "${it.main.temp.toInt()}°C", color = Color.White)
//                        Text(
//                            text = "${it.main.humidity}",
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                        Text(
//                            text = "${it.weather[0].description}",
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                    }
//                }
                }
            }
        }
    }
}