package com.example.weather_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.ui.theme.screens.Data
import com.example.weather_app.ui.theme.screens.Diseases
import com.example.weather_app.ui.theme.screens.Home
import com.example.weather_app.model.Screens
import com.example.weather_app.navigation.navGraph


import com.example.weather_app.ui.theme.DarkBlueJC
import com.example.weather_app.ui.theme.screens.WeatherS
import com.example.weather_app.ui.theme.Weather_AppTheme
import com.example.weather_app.ui.theme.screens.Knowledge
import com.example.weather_app.ui.theme.screens.Login
import com.example.weather_app.ui.theme.screens.News
import com.example.weather_app.ui.theme.screens.OTP
import com.example.weather_app.ui.theme.screens.Signup
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            Weather_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomAppBar()

                }
            }
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun WeatherPreview(){
//    Weather_AppTheme {
//        WeatherScreen()
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BottomAppBar(){
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .background(BlueJC)
                    .fillMaxWidth()
                    .height(150.dp)){
                    Text(text = "Hello")
                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "Home", color = BlueJC) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Home.screens) {
                            popUpTo(0)
                        }},
                    icon = { Icon(imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = BlueJC)})
                NavigationDrawerItem(label = { Text(text = "Logout", color = BlueJC) },
                    selected = false,
                    onClick = {
                        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
                        },
                    icon = { Icon(imageVector = Icons.Default.Home,
                        contentDescription = "logout",
                        tint = BlueJC)})
            }
        }
    ) {
        Scaffold(
            topBar = {

                TopAppBar(title = { Text(text = "Hello") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = BlueJC,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "MenuButton"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Notification",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.NotificationsActive,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Notification",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.PersonOutline,
                                contentDescription = "Localized description",
                            )
                        }
                    })

            },
            bottomBar = {

                androidx.compose.material3.BottomAppBar(
                    containerColor = BlueJC
                ) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navigationController.navigate(Screens.Home.screens)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                        )
                    }
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.List
                            navigationController.navigate(Screens.Data.screens)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.List) Color.White else Color.DarkGray
                        )
                    }
                }
            }
        ) {
//            navGraph(navController = navigationController)
                paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = Screens.Home.screens,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screens.Home.screens) { Home(navigationController) }
                composable(Screens.Data.screens) { Data() }
                composable(Screens.Weather.screens) { WeatherS() }
                composable(Screens.Diseases.screens) { Diseases() }
                composable(Screens.News.screens) { News() }
                composable(Screens.Knowledge.screens) { Knowledge() }
//            composable("Login"){ Login(navigationController) }
//            composable("Signup"){ Signup(navigationController) }
//            composable("Otp?email={email}&password={password}",
//                arguments = listOf(
//                    navArgument(name = "email"){
//                        type = NavType.StringType
//                    },
//                    navArgument(name = "password"){
//                        type = NavType.StringType
//                    }
//                )
//            ){  backStackEntry -> OTP(navController = navigationController,
//                email = backStackEntry.arguments?.getString("email")!!,
//                password = backStackEntry.arguments?.getString("password")!!) }
            }
        }
    }
}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScaffold(
//    topBar: @Composable (() -> Unit) = {},
//    bottomBar: @Composable (() -> Unit) = {},
//    content: @Composable (PaddingValues) -> Unit){
//    Scaffold(
//        bottomBar = bottomBar,
//        topBar = topBar,
//        content = content
//    )
//}
//
