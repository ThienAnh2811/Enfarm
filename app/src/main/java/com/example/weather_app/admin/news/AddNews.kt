package com.example.weather_app.admin.news

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weather_app.model.News
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.viewmodel.NewsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNews(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    )
}

@SuppressLint("SuspiciousIndentation")
@JvmOverloads
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewsScreen(navController: NavHostController, viewModel: NewsViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val context = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "News",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "Title:", fontSize = 25.sp)
            AddNews(value = title, onValueChange = { title = it })
            Text(text = "Description:", fontSize = 25.sp)
            AddNews(value = desc, onValueChange = { desc = it })
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val news = News(
                        title = title,
                        desc = desc
                    )
                          viewModel.insert(news)
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.AdminHome.screens){
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp)
                    .height(30.dp)
                    .background(BlueJC),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = BlueJC)
            ) {
                Text(
                    "ADD",
                    fontSize = 15.sp
                )
            }
        }
    }
}