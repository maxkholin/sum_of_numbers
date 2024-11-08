package com.example.sumofnumbers.domain.usecases

import com.example.sumofnumbers.domain.entity.GameSettings
import com.example.sumofnumbers.domain.entity.Level
import com.example.sumofnumbers.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {

        return repository.getGameSettings(level)
    }

}