package com.example.sumofnumbers.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sumofnumbers.R
import com.example.sumofnumbers.data.GameRepositoryImpl
import com.example.sumofnumbers.domain.entity.GameResult
import com.example.sumofnumbers.domain.entity.GameSettings
import com.example.sumofnumbers.domain.entity.Level
import com.example.sumofnumbers.domain.entity.Question
import com.example.sumofnumbers.domain.usecases.GenerateQuestionUseCase
import com.example.sumofnumbers.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {

    private lateinit var gameSettings: GameSettings

    private val repository = GameRepositoryImpl
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentageOfRightAnswer = MutableLiveData<Int>()
    val percentageOfRightAnswer: LiveData<Int>
        get() = _percentageOfRightAnswer

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercentage = MutableLiveData<Boolean>()
    val enoughPercentage: LiveData<Boolean>
        get() = _enoughPercentage

    private val _minPercentage = MutableLiveData<Int>()
    val minPercentage: LiveData<Int>
        get() = _minPercentage

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings() {
        gameSettings = getGameSettingsUseCase(level)
        _minPercentage.value = gameSettings.minPercentageOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun updateProgress() {
        _percentageOfRightAnswer.value = calculatePercentageOfRightAnswer()
        _progressAnswers.value = String.format(
            application.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentage.value =
            percentageOfRightAnswer.value!! >= gameSettings.minPercentageOfRightAnswers
    }

    private fun calculatePercentageOfRightAnswer(): Int {
        return if (countOfQuestions == 0) {
            0
        } else {
            (countOfRightAnswers / countOfQuestions.toDouble() * 100).toInt()
        }
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
        calculatePercentageOfRightAnswer()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (seconds / SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            win = enoughCount.value == true && enoughPercentage.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestion = countOfQuestions,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {

        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }

}