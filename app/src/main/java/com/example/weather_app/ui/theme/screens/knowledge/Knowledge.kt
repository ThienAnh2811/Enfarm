package com.example.weather_app.ui.theme.screens.knowledge

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weather_app.data.knowledge.FirebaseKnowledgeRepository
import com.example.weather_app.data.news.FirebaseNewsRepository
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.viewmodel.KnowledgeViewModel
import com.example.weather_app.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Knowledge(navController: NavHostController, viewModel: KnowledgeViewModel = viewModel()) {
    val firebaseRepository = FirebaseKnowledgeRepository()
    var KnowledgeList by remember { mutableStateOf(listOf<Knowledge>()) }
    var filteredKnowledgeList by remember { mutableStateOf(listOf<Knowledge>()) }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        firebaseRepository.getAllKnowledgeFromFirebase { fetchedNews ->
            KnowledgeList = fetchedNews
            filteredKnowledgeList = fetchedNews
            isLoading = false
        }
    }

    fun filterNews(query: String) {
        filteredKnowledgeList = if (query.isEmpty()) {
            KnowledgeList
        } else {
            KnowledgeList.filter { it.title.contains(query, ignoreCase = true) }
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Knowledge",
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
        ){

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    items(filteredKnowledgeList) { knowledge ->
                        KnowledgeCard(
                            knowledge = knowledge,
                            onClick = {
                                navController.navigate(Screens.KnowledgeDetail.createRoute(navController, knowledge.title))
                            }
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun KnowledgeCard(
    knowledge: Knowledge,
    onClick: () -> Unit
) {
    val modifier = Modifier
        .clickable { onClick() }
        .padding(horizontal = 16.dp, vertical = 10.dp)
        .fillMaxWidth()
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueJC
        )
    ) {
        Column {
            if (knowledge.thumbnail.isNotEmpty()) {
                val bitmap = BitmapFactory.decodeByteArray(knowledge.thumbnail, 0, knowledge.thumbnail.size)
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
                    text = knowledge.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = knowledge.desc,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

        }
    }
}

