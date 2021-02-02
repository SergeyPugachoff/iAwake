package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository

class SetVolumeUseCase(private val audioSettingsRepository: AudioSettingsRepository) {

    operator fun invoke(percent: Int) {
        return audioSettingsRepository.setVolume(percent)
    }

}