package com.example.weather_app.admin.news

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weather_app.data.news.FirebaseNewsRepository
import com.example.weather_app.model.News
import com.example.weather_app.ui.theme.DarkBlueJC


@Composable
fun NewsList(newsList: List<News>, navController: NavHostController){
//     val firebaseRepository: FirebaseNewsRepository = FirebaseNewsRepository()
//    Column {
//        SearchBar()
//
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
//        ) {
//            items(newsList) { news ->
//                NewCard(news = news)
//            }
//        }
//    }
    val firebaseRepository = FirebaseNewsRepository()

    // State for news list and loading status
    var newsList by remember { mutableStateOf(listOf<News>()) }
    var isLoading by remember { mutableStateOf(true) } // Loading state

    // Fetch the news data
    LaunchedEffect(Unit) {
        firebaseRepository.getAllNewsFromFirebase { fetchedNews ->
            newsList = fetchedNews
            isLoading = false // Set loading to false after fetching
        }
    }

    Column {
        SearchBar()

        if (isLoading) {
            // Display a loading indicator while data is being fetched
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Show news list when data is available
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
            ) {
                items(newsList) { news ->
                    NewCard(news = news)
                }
            }
        }
    }
}

@Composable
fun NewCard(news: News) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkBlueJC
        )
    ) {
        Column {
            // Check if the thumbnail is not empty and convert it to a Bitmap
            if (news.thumbnail.isNotEmpty()) {
                val bitmap = BitmapFactory.decodeByteArray(news.thumbnail, 0, news.thumbnail.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = news.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = news.desc,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    onQueryChanged: (String) -> Unit = {},
    onSearchAction: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
                onQueryChanged(newQuery)
            },
            placeholder = { Text(text = placeholder) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchAction()
                }
            ),
            singleLine = true,
            maxLines = 1
        )
    }
}
