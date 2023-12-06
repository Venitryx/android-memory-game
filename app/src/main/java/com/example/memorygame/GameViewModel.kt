package com.example.memorygame
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.update //7.3
import com.example.memorygame.data.MAX_NUM_ROUNDS
import com.example.memorygame.data.EASY_LENGTH_INCREASE
import com.example.memorygame.data.NORMAL_LENGTH_INCREASE
import com.example.memorygame.data.HARD_LENGTH_INCREASE
import com.example.memorygame.data.EASY_DURATION_PER_ROUND
import com.example.memorygame.data.NORMAL_DURATION_PER_ROUND
import com.example.memorygame.data.HARD_DURATION_PER_ROUND
import com.example.memorygame.data.STARTING_DIGIT_COUNT
import com.example.memorygame.ui.Difficulty
import com.example.memorygame.ui.GameUiState

class GameViewModel: ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentAnswer: String //allow

    var userGuess by mutableStateOf("") //6.5 b
        private set

    private fun generateNewDigit(): Int {

        val random = java.util.Random()
        return random.nextInt(10)
    }

    private fun generateNewDigits(numDigits: Int) {
        for (i in 1..numDigits) {
            currentAnswer += generateNewDigit()
        }
    }

    fun resetGame() {
        currentAnswer = ""
        generateNewDigits(STARTING_DIGIT_COUNT)
        _uiState.value = GameUiState(currentAnswer = currentAnswer)
    }

    init {
        resetGame()
    }

    fun updateUserGuess(guessedAnswer: String) { //6.4 b
        userGuess = guessedAnswer
    }

    fun getAnswer() : String {
        return currentAnswer
    }

    fun checkUserGuess(gameOverScreenFunction: () -> Unit,
                       nextRoundScreenFunction: () -> Unit) {
        //this function is based on Unscramble Lab 2 from Week 11, Fall 2023
        //https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state

        //if user guess is correct, increase the score
        //the else statement allows for displaying an error message by flagging guess as wrong
        //once time is up, the question will be marked wrong via the countdown on the Response screen
        if (userGuess.equals(currentAnswer, ignoreCase = true)) {
            _uiState.update { currentState ->
                currentState.copy(questionsRight = _uiState.value.questionsRight.inc())
            }
            isGameOver(gameOverScreenFunction, nextRoundScreenFunction)
        } else {
            //7.3
            _uiState.update { currentState ->
                currentState.copy(isGuessWrong = true)
            }
        }
    }

    fun isGameOver(gameOverScreenFunction: () -> Unit,
                           nextRoundScreenFunction: () -> Unit) {
        //this function is based on Unscramble Lab 2 from Week 11, Fall 2023
        //https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state
        //step 9
        //sees if the round is the final round, and if so
        if (_uiState.value.roundNumber >= MAX_NUM_ROUNDS) {
            onGameOver(gameOverScreenFunction)
        }
        else {
            nextRound(nextRoundScreenFunction)
        }
    }

    private fun onGameOver(gameOverScreenFunction: () -> Unit) {
        _uiState.update { currentState ->
            currentState.copy(
                isGuessWrong = false,
                isGameOver = true
            )
        }
        gameOverScreenFunction.invoke()
    }

    private fun nextRound(nextRoundScreenFunction: () -> Unit) {
        _uiState.update { currentState ->
            generateNewDigits(getNewRoundLengthIncrease(currentState.difficulty))
            currentState.copy(
                isGuessWrong = false,
                currentAnswer = currentAnswer,
                roundNumber = _uiState.value.roundNumber.inc()
            )
        }
        updateUserGuess("")
        nextRoundScreenFunction.invoke()
    }

    fun markQuestionWrong() {
        _uiState.update { currentState ->
            currentState.copy(questionsWrong = _uiState.value.questionsWrong.inc(),
            isGuessWrong = false)
        }
    }

    fun changeDifficulty(newDifficulty: Difficulty) {
        _uiState.update { currentState ->
            currentState.copy(
                difficulty = newDifficulty
            )
        }
    }

    fun getCountdownTimeFromDifficulty(difficulty: Difficulty) : Int {
        return when(difficulty) {
            Difficulty.Easy -> EASY_DURATION_PER_ROUND
            Difficulty.Normal -> NORMAL_DURATION_PER_ROUND
            Difficulty.Hard -> HARD_DURATION_PER_ROUND
        }
    }

    private fun getNewRoundLengthIncrease(difficulty: Difficulty) : Int {
        return when(difficulty) {
            Difficulty.Easy -> EASY_LENGTH_INCREASE
            Difficulty.Normal -> NORMAL_LENGTH_INCREASE
            Difficulty.Hard -> HARD_LENGTH_INCREASE
        }
    }
}