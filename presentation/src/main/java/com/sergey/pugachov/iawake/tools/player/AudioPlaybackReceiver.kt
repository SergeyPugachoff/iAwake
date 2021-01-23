package com.sergey.pugachov.iawake.tools.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class AudioPlaybackReceiver(
    private val onReceive: (playerState: PlayerState) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.getParcelableExtra<PlayerState>(STATE_ARG)?.let { playerState ->
            onReceive(playerState)
        }
    }

    companion object {
        const val ACTION = "AudioPlaybackReceiver.action"
        private const val STATE_ARG = "AudioPlaybackReceiver.player_state_arg"

        fun sendBroadcast(context: Context, playerState: PlayerState) {
            val intent: Intent = Intent().apply {
                action = ACTION
                putExtra(STATE_ARG, playerState)
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
    }
}