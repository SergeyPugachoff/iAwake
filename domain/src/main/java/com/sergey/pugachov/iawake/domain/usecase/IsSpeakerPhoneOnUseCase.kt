package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository

class IsSpeakerPhoneOnUseCase(private val audioSettingsRepository: AudioSettingsRepository) {

    operator fun invoke(): Boolean {
        return audioSettingsRepository.isSpeakerPhoneOn()
    }

}