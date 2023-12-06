package com.example.memorygame.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.data.CUSTOM_BUTTON_SHAPE
import com.example.memorygame.data.MAX_NUM_ROUNDS
import kotlinx.coroutines.delay

@Composable
//Displays the text that the user has to memorize, and input on the next screen.
fun CallScreen(
    number: String,
    roundNumber: Int,
    onPause: () -> Unit,
    countdownTimeInSeconds: Int,
    onFinishCountdown: () -> Unit,
    onReady: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6200EA), Color(0xFF03A9F4))
                )
            )
    ) {
        PauseButton (
            onClick = onPause,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(100.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Remember this number!",
                style = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White
                ),
                softWrap = true,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = number,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                overflow = TextOverflow.Visible,
                softWrap = true,
                maxLines = 5,
                minLines = 1,
                letterSpacing = 10.sp
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Round $roundNumber/$MAX_NUM_ROUNDS",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            CountdownTimer(timeInSeconds = countdownTimeInSeconds, onFinish = onFinishCountdown)

            Button(
                onClick = onReady,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Ready", color = Color.White)
            }
        }
    }
}


//based on https://medium.com/@android-world/jetpack-compose-countdown-timer-9531dd3119a6
//Phil Dukhov - https://stackoverflow.com/questions/71191340/how-can-i-implement-a-timer-in-a-portable-way-in-jetpack-compose
@Composable
fun CountdownTimer(
    timeInSeconds: Int,
    onFinish: () -> Unit
) {
    var timeLeft by rememberSaveable { mutableIntStateOf(timeInSeconds) }

    LaunchedEffect(Unit) {//2nd link
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        onFinish()
    }


    Box() {
        Text(
            text = "${timeLeft}s",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}