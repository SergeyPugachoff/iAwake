package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository

class GetCurrentVolumeUseCase(private val audioSettingsRepository: AudioSettingsRepository) {

    operator fun invoke(): Int {
        return audioSettingsRepository.getCurrentVolume()
    }

}