package com.example.weather_app.ui.theme.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocal
import androidx.compose.ui.modifier.modifierLocalProvider
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
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavHostController){
    val images = listOf(
        R.drawable.firefly,
        R.drawable.marisa
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(Unit){
        while (true){
            delay(2000)
            val nextPage = (pagerState.currentPage + 1)%pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

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
            Spacer(modifier = Modifier.height(60.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)) {
                HorizontalPager(state = pagerState,
                    modifier = Modifier.wrapContentSize()) {
                    currentPage -> Card(
                        modifier = Modifier.wrapContentSize(),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(painter = painterResource(id = images[currentPage]),
                            contentDescription = "")
                }
                }
//                PageIndicator(pageCount = images.size,
//                    currentPage = pagerState.currentPage)

            }

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

//@Composable
//fun PageIndicator(pageCount: Int, currentPage: Int) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        repeat(pageCount) {
//            IndicatorDots(isSelected = it == currentPage)
//        }
//    }
//}
//
//@Composable
//fun IndicatorDots(isSelected: Boolean) {
//    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp
//        , label ="")
//    Box(modifier = Modifier
//        .padding(2.dp)
//        .size(size.value)
//        .clip(CircleShape)
//        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737)))
//}
