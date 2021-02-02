package com.sergey.pugachov.iawake.domain.repository

interface AudioSettingsRepository {

    fun getCurrentVolume(): Int

    fun setVolume(percent: Int)

    fun isSpeakerPhoneOn(): Boolean

    fun setSpeakerPhoneOn(on: Boolean)

}