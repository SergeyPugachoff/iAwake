package com.sergey.pugachov.iawake.ui.tracks.audiosettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergey.pugachov.iawake.domain.usecase.GetCurrentVolumeUseCase
import com.sergey.pugachov.iawake.domain.usecase.IsSpeakerPhoneOnUseCase
import com.sergey.pugachov.iawake.domain.usecase.SetSpeakerPhoneOnUseCase
import com.sergey.pugachov.iawake.domain.usecase.SetVolumeUseCase

class AudioSettingsViewModel(
    private val getCurrentVolumeUseCase: GetCurrentVolumeUseCase,
    private val setVolumeUseCase: SetVolumeUseCase,
    private val isSpeakerPhoneOnUseCase: IsSpeakerPhoneOnUseCase,
    private val setSpeakerPhoneOnUseCase: SetSpeakerPhoneOnUseCase,
) : ViewModel() {

    private val _volumePercent = MutableLiveData<Int>()

    val volumePercent: LiveData<Int> = _volumePercent
    var isSpeakerPhoneOn: Boolean = isSpeakerPhoneOnUseCase()

    init {
        _volumePercent.value = getCurrentVolumeUseCase()
    }

    fun setVolume(percent: Int) {
        setVolumeUseCase(percent)
    }

    fun onSpeakerPhone(on: Boolean) {
        setSpeakerPhoneOnUseCase(on)
        isSpeakerPhoneOn = on
    }
}