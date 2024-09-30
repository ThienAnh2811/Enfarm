package com.example.weather_app.admin.knowledge

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weather_app.data.knowledge.FirebaseKnowledgeRepository
import com.example.weather_app.data.news.FirebaseNewsRepository
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News
import com.example.weather_app.ui.theme.DarkBlueJC


@Composable
fun KnowledgeList(navController: NavHostController) {
    val firebaseRepository = FirebaseKnowledgeRepository()
    var KnowledgeList by remember { mutableStateOf(listOf<Knowledge>()) }
    var filteredKnowledgeList by remember { mutableStateOf(listOf<Knowledge>()) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var selectedKnowledge by remember { mutableStateOf(setOf<Knowledge>()) }
    LaunchedEffect(Unit) {
        firebaseRepository.getAllKnowledgeFromFirebase { fetchedKnowledge ->
            KnowledgeList = fetchedKnowledge
            filteredKnowledgeList = fetchedKnowledge
            isLoading = false
        }
    }

    fun filterKnowledge(query: String) {
        filteredKnowledgeList = if (query.isEmpty()) {
            KnowledgeList
        } else {
            KnowledgeList.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun deleteSelectedKnowledge() {
        selectedKnowledge.forEach { news ->
            firebaseRepository.deleteKnowledgeFromFirebase(news.title)
        }
        KnowledgeList = KnowledgeList.filterNot { selectedKnowledge.contains(it) }
        filteredKnowledgeList = filteredKnowledgeList.filterNot { selectedKnowledge.contains(it) }
        selectedKnowledge = setOf()
    }

    Column {
        if (selectedKnowledge.isNotEmpty()) {
            SelectionToolbar(selectedCount = selectedKnowledge.size, onDelete = { deleteSelectedKnowledge() })
        }

        SearchBar(onQueryChanged = { query ->
            searchQuery = query
            filterKnowledge(query)
        })

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
            ) {
                items(filteredKnowledgeList) { knowledge ->
                    KnowledgeCard(
                        knowledge = knowledge,
                        isSelected = selectedKnowledge.contains(knowledge),
                        onLongPress = { selectedKnowledge = selectedKnowledge + knowledge },
                        onCancel = { selectedKnowledge = selectedKnowledge - knowledge },
                        onNavigate = { }
                    )
                }
            }
        }
    }
}

@Composable
fun KnowledgeCard(
    knowledge: Knowledge,
    isSelected: Boolean,
    onLongPress: () -> Unit,
    onCancel: () -> Unit,
    onNavigate: () -> Unit,
) {
    val modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 10.dp)
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    onLongPress()
                }
            )
        }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else DarkBlueJC
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

            if (isSelected) {
                // Show X (cancel) and arrow (navigate) when the card is selected
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onCancel) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_delete),
                            contentDescription = "Cancel",
                            tint = Color.Red
                        )
                    }
                    IconButton(onClick = onNavigate) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_more),
                            contentDescription = "Navigate",
                            tint = Color.Green
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    onQueryChanged: (String) -> Unit
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
                onQueryChanged(newQuery)  // Pass the updated query back to the parent composable
            },
            placeholder = { Text(text = placeholder) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            maxLines = 1
        )
    }
}

@Composable
fun SelectionToolbar(selectedCount: Int, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$selectedCount selected", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_delete),
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}
