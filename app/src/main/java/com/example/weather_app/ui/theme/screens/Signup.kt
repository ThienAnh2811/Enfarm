package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(navController: NavHostController) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", fontSize = 35.sp, fontWeight = FontWeight.Bold)

        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "Signup Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(bottom = 88.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            isError = name.isEmpty(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            isError = email.isEmpty(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", fontWeight = FontWeight.Bold) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            isError = password.isEmpty(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm Password", fontWeight = FontWeight.Bold) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            isError = confirmPassword.isEmpty() || confirmPassword != password,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    navController.navigate(Screens.Otp.createRoute(navController, email, password))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = BlueJC)
        ) {
            Text("NEXT", fontSize = 15.sp)
        }
    }
}
