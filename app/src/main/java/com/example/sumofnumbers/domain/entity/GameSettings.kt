package com.example.sumofnumbers.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentageOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)