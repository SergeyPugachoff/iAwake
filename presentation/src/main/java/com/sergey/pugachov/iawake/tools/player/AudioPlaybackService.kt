package com.sergey.pugachov.iawake.tools.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerNotificationManager.createWithNotificationChannel
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sergey.pugachov.iawake.BuildConfig
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.ui.HostActivity

class AudioPlaybackService : Service() {

    private lateinit var player: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager
    private val playerEventListener = object : Player.EventListener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            val currentState = when {
                isPlaying -> PlayerState.Playing
                player.playbackState == Player.STATE_BUFFERING -> PlayerState.Playing
                player.playbackState == Player.STATE_READY -> PlayerState.Paused
                else -> PlayerState.Stopped
            }
            AudioPlaybackReceiver.sendBroadcast(applicationContext, currentState)
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            AudioPlaybackReceiver.sendBroadcast(applicationContext, PlayerState.Error)
        }
    }
    private val mediaDescriptionAdapter =
        object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): CharSequence = title

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                return PendingIntent.getActivity(
                    applicationContext,
                    0,
                    Intent(applicationContext, HostActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

            override fun getCurrentContentText(player: Player): CharSequence? = null

            override fun getCurrentLargeIcon(
                player: Player,
                callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? = null
        }
    private val notificationListener = object : PlayerNotificationManager.NotificationListener {
        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            if (ongoing) {
                startForeground(notificationId, notification)
            } else {
                stopForeground(false)
            }
        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopSelf()
        }
    }
    private var title = ""

    override fun onBind(intent: Intent): IBinder {
        return AudioPlaybackServiceBinder()
    }

    override fun onCreate() {
        super.onCreate()

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_SPEECH)
            .build()

        player = SimpleExoPlayer.Builder(applicationContext)
            .setAudioAttributes(audioAttributes, true)
            .build()

        player.addListener(playerEventListener)

        playerNotificationManager = createWithNotificationChannel(
            applicationContext,
            NOTIFICATION_CHANNEL_ID,
            R.string.app_name,
            0,
            NOTIFICATION_ID,
            mediaDescriptionAdapter,
            notificationListener
        ).apply {
            setUseNextAction(false)
            setUsePreviousAction(false)
            setUseStopAction(false)
            setUsePlayPauseActions(true)
            setPlayer(player)
        }
    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player.release()

        super.onDestroy()
    }

    fun play(title: String, uri: Uri) {
        this.title = title
        val userAgent = Util.getUserAgent(applicationContext, BuildConfig.APPLICATION_ID)
        val dataSourceFactory = DefaultDataSourceFactory(applicationContext, userAgent)
        val mediaItem = MediaItem.Builder().setUri(uri).build()
        val mediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

        player.setMediaSource(mediaSource, true)
        player.prepare()
        player.playWhenReady = true
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun resume() {
        player.playWhenReady = true
    }

    inner class AudioPlaybackServiceBinder : Binder() {
        val service
            get() = this@AudioPlaybackService
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "playback_channel"
        private const val NOTIFICATION_ID = 101
    }
}