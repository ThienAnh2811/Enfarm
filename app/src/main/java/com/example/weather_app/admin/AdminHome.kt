package com.example.weather_app.admin

import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.ui.theme.WeatherCard
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHome(navController: NavHostController, email: String){
    Scaffold(topBar = {

            TopAppBar(
                title = { Text(text = "Hello Admin") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BlueJC,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ))
    }
            ) {
            paddingValues -> Column(
                modifier = Modifier.padding(paddingValues)
        ) {
            NewsCard(navController)
            KnowledgeCard(navController)
        }

    }
}



@Composable
fun NewsCard(navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(280.dp) // Adjust the size to match the image proportions
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { /* Handle card click if needed */ },
        colors = CardDefaults.cardColors(
            containerColor = BlueJC
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Small square box
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color(0xFFB2FF59), shape = RoundedCornerShape(4.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Newspaper,
                            contentDescription = "Worklist Icon",
                            tint = Color.Black, // Same green color
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Text below the square
                Text(
                    text = "News",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
            // Number in the bottom right corner
//            Text(
//                text = "6",
//                color = Color.White,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.align(Alignment.BottomEnd)
//            )
            Icon(
                imageVector = Icons.Default.AddCard,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Screens.AddNews.screens)
                    }
            )
            Icon(
                imageVector = Icons.Default.ManageSearch,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Screens.ManageNews.screens)
                    }
            )
        }
    }
}

@Composable
fun KnowledgeCard(navController: NavHostController){
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(280.dp) // Adjust the size to match the image proportions
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BlueJC
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(BlueJC)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Small square box
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color(0xFFB2FF59), shape = RoundedCornerShape(4.dp))
                    ){
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "Worklist Icon",
                            tint = Color.Black, // Same green color
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    // Icon next to the text
//                    Icon(
//                        imageVector = Icons.Default.CheckCircle,
//                        contentDescription = "Worklist Icon",
//                        tint = Color(0xFFB2FF59), // Same green color
//                        modifier = Modifier.size(16.dp)
//                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Text below the square
                Text(
                    text = "Knowledge",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
            Icon(imageVector = Icons.Default.AddCard,
                contentDescription = "",
                modifier = Modifier.align(Alignment.BottomStart)
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Screens.AddKnowledge.screens)
                    },
            )
            Icon(imageVector = Icons.Default.ManageSearch,
                contentDescription = "",
                modifier = Modifier.align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Screens.ManageKnowledge.screens)
                    },
            )
        }
    }
}