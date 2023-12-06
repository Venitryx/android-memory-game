package com.example.memorygame.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.data.CUSTOM_BUTTON_SHAPE

@Composable
fun MainMenuScreen(
    onDifficultyChanged: (Difficulty) -> Unit = {},
    difficulty: Difficulty,
    onStartButtonClicked: () -> Unit
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Memory Game",
                style = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF03A9F4),
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DifficultySelector(
                onDifficultyChanged = onDifficultyChanged
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Difficulty Selected",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = difficulty.name,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onStartButtonClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Play", color = Color.White)
            }
        }
    }
}

//this function is based on the SelectOptionScreen.kt from the Cupcake lab.
@Composable
fun DifficultySelector(
    onDifficultyChanged: (Difficulty) -> Unit = {}
) {
    var selectedDifficulty by rememberSaveable { mutableStateOf(Difficulty.Easy) }
    val difficultyOptions = Difficulty.values()

    difficultyOptions.forEach { difficulty ->
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(
                    enabled = selectedDifficulty == difficulty,
                    onClick = {
                        selectedDifficulty = difficulty
                        onDifficultyChanged(difficulty)
                    }
                )
        ) {
            OutlinedButton(
                onClick = {
                    selectedDifficulty = difficulty
                    onDifficultyChanged(difficulty)
                },
                //

                //https://stackoverflow.com/questions/64376333/background-color-on-button-in-jetpack-compose
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = difficulty.name, color = Color.White)
            }
        }
    }
}