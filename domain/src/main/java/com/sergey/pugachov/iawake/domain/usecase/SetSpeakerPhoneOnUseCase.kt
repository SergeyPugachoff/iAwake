package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository

class SetSpeakerPhoneOnUseCase(private val audioSettingsRepository: AudioSettingsRepository) {

    operator fun invoke(isOn: Boolean) {
        return audioSettingsRepository.setSpeakerPhoneOn(isOn)
    }

}