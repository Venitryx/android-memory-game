package com.example.memorygame.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.data.CUSTOM_BUTTON_SHAPE

// ResultsLayout.kt
@Composable
fun ResultsScreen(
    correctAnswers: Int,
    wrongAnswers: Int,
    accuracyPercentage: Int,
    mainMenuScreenFunction: () -> Unit
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
                text = "Results",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ResultItem(title = "Correct Answers", value = "$correctAnswers")
            ResultItem(title = "Wrong Answers", value = "$wrongAnswers")
            ResultItem(title = "Accuracy Percentage", value = "$accuracyPercentage%")

            Button(
                onClick = mainMenuScreenFunction,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Back to Main Menu", color = Color.White)
            }

        }
    }
}

@Composable
fun ResultItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}
