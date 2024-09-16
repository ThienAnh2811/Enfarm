package com.example.weather_app.admin

import android.widget.Toast
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLogin(navController: NavHostController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, description) = createRefs()

        Box(
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(300.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .clip(CutCornerShape(bottomEnd = 30.dp))
        ){
            Image(
                painter = painterResource(id = R.drawable.admin),
                contentDescription = "Admin Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .padding(bottom = 60.dp)
            )

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "LOGIN",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            placeholder = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = email.isEmpty(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            placeholder = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            isError = password.isEmpty(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        Button(
                            onClick = {
                                if (password.isEmpty()) {
                                    Toast.makeText(context, "Enter the password", Toast.LENGTH_SHORT).show()
                                } else if (email.isEmpty()) {
                                    Toast.makeText(context, "Enter the email", Toast.LENGTH_SHORT).show()
                                } else {
                                    auth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                navController.navigate(Screens.AdminHome.createRoute(navController, email)){
                                                    popUpTo(0)
                                                }
                                            } else {
                                                Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .background(BlueJC),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = BlueJC)
                        ) {
                            Text(
                                "LOGIN",
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}