package com.example.sumofnumbers.domain.entity

data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
)