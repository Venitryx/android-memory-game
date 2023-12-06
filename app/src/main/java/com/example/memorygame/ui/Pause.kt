package com.example.memorygame.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.R
import com.example.memorygame.data.CUSTOM_BUTTON_SHAPE

//Zeno
@Composable
fun PauseScreen(
    onResume: () -> Unit,
    onEnd: () -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Game Paused",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = onResume,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "Resume", color = Color.White)
            }

            Button(
                onClick = onEnd,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "End", color = Color.White)
            }
        }
    }
}

@Composable
//Vincent - based on Greetings Lab - https://developer.android.com/codelabs/basic-android-kotlin-compose-add-images#3
fun PauseButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    val image = painterResource(R.drawable.pause_icon) //https://www.seekpng.com/png/full/294-2944357_pause-symbol-png.png
    Button(onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Image(painter = image, contentDescription = null)
    }
}
