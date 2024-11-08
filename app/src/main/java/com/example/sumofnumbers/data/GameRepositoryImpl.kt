package com.example.sumofnumbers.data

import com.example.sumofnumbers.domain.entity.GameSettings
import com.example.sumofnumbers.domain.entity.Level
import com.example.sumofnumbers.domain.entity.Question
import com.example.sumofnumbers.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 3
    private const val MIN_VISIBLE_NUMBER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)

        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUMBER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }

        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {

            Level.EASY -> GameSettings(
                10,
                10,
                60
            )

            Level.MEDIUM -> GameSettings(
                20,
                15,
                75
            )

            Level.HARD -> GameSettings(
                30,
                20,
                90
            )


        }
    }
}