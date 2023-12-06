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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.R
import com.example.memorygame.data.CUSTOM_BUTTON_SHAPE
import com.example.memorygame.data.MAX_NUM_ROUNDS

// Code written mainly by Zeno
@Composable
fun ResponseScreen(
    countdownTimeInSeconds: Int,
    userGuess: String,
    isGuessWrong: Boolean,
    roundNumber: Int,
    onUserGuessChanged: (String) -> Unit,
    onSubmitGuessOrKeyboardDone: () -> Unit,
    onFinishCountdownOrSkip: () -> Unit,
    onPause: () -> Unit
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Round $roundNumber/$MAX_NUM_ROUNDS",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Type in the previous number!",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //based on Unscrambled words lab
            OutlinedTextField(
                value = userGuess,
                shape = CUSTOM_BUTTON_SHAPE,
                onValueChange = onUserGuessChanged,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    errorCursorColor = Color(0xFF990000),
                    errorContainerColor = Color.White
                ),
                label = {
                    if (isGuessWrong) {
                        Text(text = stringResource(R.string.wrong_guess),
                            color = Color.Red
                            )
                    } else {
                        Text(text = stringResource(R.string.enter_digits),
                            color = Color.White)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                singleLine = true,
                isError = isGuessWrong, //7.3b changed from default false to appear red an error if guess is wrong

                //https://stackoverflow.com/questions/66482473/how-to-set-the-inputtype-for-a-textfield-in-jetpack-compose
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSubmitGuessOrKeyboardDone() } //step 6.1 b
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSubmitGuessOrKeyboardDone,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Submit", color = Color.White)
            }
            CountdownTimer(timeInSeconds = countdownTimeInSeconds, onFinish = onFinishCountdownOrSkip)
            Button(
                onClick = onFinishCountdownOrSkip,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF990000)),
                border = BorderStroke(1.dp, Color.White),
                shape = CUSTOM_BUTTON_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Skip", color = Color.White)
            }
        }
    }
}

