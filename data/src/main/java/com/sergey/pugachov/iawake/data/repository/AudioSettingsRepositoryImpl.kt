package com.sergey.pugachov.iawake.data.repository

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository

class AudioSettingsRepositoryImpl(private val context: Context) : AudioSettingsRepository {

    private val audioManager: AudioManager by lazy {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    private val maxVolume: Int by lazy {
        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    private val devices: Array<AudioDeviceInfo>
        get() = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)

    private val hasBluetoothHeadset: Boolean
        get() = devices.any {
            it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                    || it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
        }

    private val hasWiredHeadset: Boolean
        get() = devices.any {
            it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET
                    || it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
        }

    override fun getCurrentVolume(): Int {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        return volumeToPercent(currentVolume)
    }

    override fun setVolume(percent: Int) {
        val volume = percentToVolume(percent)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
    }

    override fun isSpeakerPhoneOn(): Boolean {
        return !hasBluetoothHeadset && !hasWiredHeadset || audioManager.isSpeakerphoneOn
    }

    override fun setSpeakerPhoneOn(on: Boolean) {
        if (on) {
            if (hasBluetoothHeadset) {
                audioManager.stopBluetoothSco()
                audioManager.isBluetoothScoOn = false
            }
            audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
            audioManager.isSpeakerphoneOn = true
        } else {
            if (hasBluetoothHeadset) {
                audioManager.startBluetoothSco()
                audioManager.isBluetoothScoOn = true
            }
            audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
            audioManager.isSpeakerphoneOn = false
        }
    }

    private fun percentToVolume(percent: Int): Int {
        return (percent * maxVolume) / 100
    }

    private fun volumeToPercent(volume: Int): Int {
        return (volume * 100) / maxVolume
    }
}