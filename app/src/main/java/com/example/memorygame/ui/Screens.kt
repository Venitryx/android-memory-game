package com.example.memorygame.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.GameViewModel

enum class ScreenType {
    MainMenu, Call, Response, Pause, Results
}

@Composable
fun MemoryGame(
    gameViewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val gameUiState by gameViewModel.uiState.collectAsState() //based on Week 11 Lab Unscramble

    NavHost(
        navController = navController,
        startDestination = ScreenType.MainMenu.name
    )

    {
        //composables based on Week 12/13 Lab - https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation
        composable(route = ScreenType.MainMenu.name) {
            //https://stackoverflow.com/questions/70376070/jetpack-compose-disable-back-button
            BackHandler(true) {
                //do nothing
            }

            MainMenuScreen(
                onDifficultyChanged = {
                    gameViewModel.changeDifficulty(it)
                },
                onStartButtonClicked = {
                    //do not ever include resetGame() here
                    navController.navigate(ScreenType.Call.name)
                },
                difficulty = gameUiState.difficulty
            )

        }
        composable(route = ScreenType.Call.name) {
            BackHandler(true) {
                navController.navigate(ScreenType.Pause.name)
            }
            CallScreen(
                roundNumber = gameUiState.roundNumber,
                number = gameViewModel.getAnswer(),
                countdownTimeInSeconds = gameViewModel.getCountdownTimeFromDifficulty(gameUiState.difficulty),
                onFinishCountdown = { navController.navigate(ScreenType.Response.name) },
                onPause = { navController.navigate(ScreenType.Pause.name) },
                onReady = {navController.navigate(ScreenType.Response.name) },
            )
        }
        //*Response screen is mainly based on the Unscramble Lab
        composable(route = ScreenType.Response.name) {
            BackHandler(true) {
                navController.navigate(ScreenType.Pause.name)
            }
            ResponseScreen(
                roundNumber = gameUiState.roundNumber,
                onUserGuessChanged = { gameViewModel.updateUserGuess(it) }, //updates textbox to reflect what the user typed in
                countdownTimeInSeconds = gameViewModel.getCountdownTimeFromDifficulty(gameUiState.difficulty),
                userGuess = gameViewModel.userGuess,
                //navigate to following screens if user guesses correctly
                isGuessWrong = gameUiState.isGuessWrong, //used for error textbox display
                onFinishCountdownOrSkip = { //user can submit any number of times before the countdown before it is marked wrong
                    gameViewModel.markQuestionWrong() //used solely for updating number of questions wrong
                    gameViewModel.isGameOver(
                        gameOverScreenFunction = {
                            navController.navigate(ScreenType.Results.name)
                        },
                        nextRoundScreenFunction = {
                            navController.navigate(ScreenType.Call.name)
                        }
                    )
                },
                onSubmitGuessOrKeyboardDone = {
                    gameViewModel.checkUserGuess (
                        gameOverScreenFunction = {
                            navController.navigate(ScreenType.Results.name)
                        },
                        nextRoundScreenFunction = {
                            navController.navigate(ScreenType.Call.name)
                        }
                    )
                },
                onPause = { navController.navigate(ScreenType.Pause.name) }

            )
            //*
        }
        composable(route = ScreenType.Results.name) {
            BackHandler(true) {
                resetAndGoBackToMainMenu(gameViewModel, navController)
            }
            ResultsScreen(
                correctAnswers = gameUiState.questionsRight,
                wrongAnswers = gameUiState.questionsWrong,
                accuracyPercentage = ((gameUiState.questionsRight.toFloat() /
                        (gameUiState.questionsRight + gameUiState.questionsWrong).toFloat()
                        ) * 100).toInt(), //Converting a fraction into a percentage using int-to-float conversion to fix integer division always resulting in 0
                mainMenuScreenFunction = { resetAndGoBackToMainMenu(gameViewModel, navController) }
            )
        }
        composable(route = ScreenType.Pause.name) {
            BackHandler(true) {
                navController.navigateUp()
            }
            PauseScreen(
                onResume = { navController.navigateUp() }, //go back to previous Screen
                onEnd = { resetAndGoBackToMainMenu(gameViewModel, navController) }
            )
        }
    }

}
//5.1f of Week 12/13 Cupcake Lab
private fun resetAndGoBackToMainMenu(
    //5.2f
    viewModel: GameViewModel,
    navController: NavHostController
    //
) {
    viewModel.resetGame() //5.3f
    navController.popBackStack(ScreenType.MainMenu.name, inclusive = false) //5.4f
}