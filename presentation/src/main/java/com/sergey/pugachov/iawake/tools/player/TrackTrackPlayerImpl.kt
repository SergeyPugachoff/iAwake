package com.sergey.pugachov.iawake.tools.player

import android.content.*
import android.net.Uri
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

class TrackTrackPlayerImpl(private val applicationContext: Context) : TrackPlayer {

    private lateinit var audioService: AudioPlaybackService
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            audioService = (binder as AudioPlaybackService.AudioPlaybackServiceBinder).service
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }
    private val broadcastReceiver: AudioPlaybackReceiver =
        AudioPlaybackReceiver(::handleBroadcastMessage)
    private lateinit var onStateChangedAction: (state: PlayerState) -> Unit

    init {
        registerReceiver()
        bindService()
    }

    override fun onStateChanged(action: (state: PlayerState) -> Unit) {
        onStateChangedAction = action
    }

    override fun play(track: TrackModel) {
        audioService.play(track.title, Uri.parse(track.url))
    }

    override fun pause() {
        audioService.pause()
    }

    override fun resume() {
        audioService.resume()
    }

    override fun release() {
        audioService.pause()
        unregisterReceiver()
        applicationContext.unbindService(connection)
        applicationContext.stopService(Intent(applicationContext, AudioPlaybackService::class.java))
    }

    private fun registerReceiver() {
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            broadcastReceiver,
            IntentFilter(AudioPlaybackReceiver.ACTION)
        )
    }

    private fun unregisterReceiver() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadcastReceiver)
    }

    private fun bindService() {
        val intent = Intent(applicationContext, AudioPlaybackService::class.java)
        applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun handleBroadcastMessage(playerState: PlayerState) {
        if (::onStateChangedAction.isInitialized) {
            onStateChangedAction(playerState)
        }
    }
}