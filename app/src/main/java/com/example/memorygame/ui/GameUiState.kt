package com.example.memorygame.ui

data class GameUiState (

    //based on Unscramble Lab 2 from Week 11, Fall 2023
    //https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state

    val difficulty: Difficulty = Difficulty.Easy,
    val roundNumber: Int = 1,
    val questionsRight: Int = 0,
    val questionsWrong: Int = 0,
    //the string of digits that the user has to remember - new digits are randomly chosen
    val currentAnswer: String = "",
    val isGuessWrong: Boolean = false,
    val isGameOver : Boolean = false
)

enum class Difficulty {
    Easy,
    Normal,
    Hard
}


