package com.example.sumofnumbers.domain.repository

import com.example.sumofnumbers.domain.entity.GameSettings
import com.example.sumofnumbers.domain.entity.Level
import com.example.sumofnumbers.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}