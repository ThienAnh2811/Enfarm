package com.example.weather_app.admin

import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC

//@Preview(showBackground = true)
@Composable
fun AdminHome(navController: NavHostController, email: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
            Text(text = "App Manage",
                fontSize = 50.sp,
                color = BlueJC,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.padding(10.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                Column{
                    Text(text = "News", fontSize = 30.sp)
                    Text(text = "Knowledge", fontSize = 30.sp)
                }
                Column{

                    IconButton(
                        onClick = { navController.navigate(Screens.AddNews.screens) },
                        modifier = Modifier.size(30.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(25.dp)
                                .background(BlueJC)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(25.dp)
                                .background(BlueJC)
                        )
                    }
                }
                Column{

                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier.size(30.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Filled.ManageSearch,
                            contentDescription = "Add",
                            modifier = Modifier.size(25.dp)
                                .background(BlueJC)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ManageSearch,
                            contentDescription = "Add",
                            modifier = Modifier.size(25.dp)
                                .background(BlueJC)
                        )
                    }
                }
            }
    }
}