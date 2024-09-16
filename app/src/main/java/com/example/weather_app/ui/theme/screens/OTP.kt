package com.example.weather_app.ui.theme.screens

import android.annotation.SuppressLint
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import papaya.`in`.sendmail.SendMail
import kotlin.random.Random


lateinit var auth: FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun OTP(navController: NavHostController, email: String, password: String) {
    auth = FirebaseAuth.getInstance()
    var otpText by remember { mutableStateOf("") }
    var sentOtp: Int? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // OTP Image
        Image(
            painter = painterResource(id = R.drawable.otp),
            contentDescription = "OTP",
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(bottom = 88.dp)
        )

        Text(text = "Enter the OTP", fontWeight = FontWeight.Bold)
        Text(text = "We sent you an OTP on the following email:", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(35.dp))

        OutlinedTextField(
            value = otpText,
            onValueChange = {
                if (it.length <= 4) {
                    otpText = it
                }
            },
            placeholder = { Text("OTP", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            isError = otpText.isEmpty(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.clickable {
                if (sentOtp == null) {
                    sentOtp = Random.nextInt(1000, 9999)
                    val mail = SendMail(
                        "lolicon28111@gmail.com", "gdmlhbeupxixintl", email,
                        "Login Signup app's OTP", "Your OTP is -> $sentOtp"
                    )
                    coroutineScope.launch {
                        mail.execute()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "OTP already sent. Check your email.", Toast.LENGTH_SHORT).show()
                }
            },
            text = "Didn't receive the OTP? Resend the OTP",
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(15.dp))


        Button(
            onClick = {
                if (otpText.length < 4) {
                    Toast.makeText(context, "Enter the OTP", Toast.LENGTH_SHORT).show()
                } else if (sentOtp == null) {
                    Toast.makeText(context, "Please click 'Resend OTP' to generate a code", Toast.LENGTH_SHORT).show()
                } else if (otpText != sentOtp.toString()) {
                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            navController.navigate(Screens.Home.createRoute(navController, email)){
                                popUpTo(0)
                            }
                        } else {
                            Toast.makeText(context, it.exception?.message.toString(),
                                LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueJC),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = BlueJC)
        ) {
            Text("DONE", fontSize = 15.sp)
        }
    }
}