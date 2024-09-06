package com.example.weather_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.ui.theme.BlueJC

@Preview(showBackground = true)
@Composable
fun OTP() {

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.otp),
            contentDescription = "torako",
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(bottom = 88.dp)
        )
        Text(text = "Enter the OTP", fontWeight = FontWeight.Bold)
        Text(text = "We sent you on the following email:",  fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(35.dp))
        OTPTextField()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Didn't receive the OTP? Resend the OTP",  fontWeight = FontWeight.Bold,
            color = Color.Red)
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueJC),

            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = BlueJC)
        ) {
            Text(
                "DONE",
                fontSize = 15.sp
            )
        }


    }
}

@Composable
fun OTPTextField(){
    var otptext by remember {
        mutableStateOf("")
    }
    BasicTextField(value = otptext,
        onValueChange = {
            if (it.length <= 4) {
                otptext = it
            }
        }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(4){
                    index -> val number = when{
                index >= otptext.length ->""
                else -> otptext[index]
            }
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = number.toString(),
                        style = MaterialTheme.typography.titleLarge)
                }
                Box(modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Color.Black))
            }
        }
    }
}