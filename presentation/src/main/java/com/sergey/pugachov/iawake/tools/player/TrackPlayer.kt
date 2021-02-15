package com.sergey.pugachov.iawake.tools.player

interface TrackPlayer {

    fun play(title: String, imageUrl: String, mediaUrl: String)

    fun pause()

    fun resume()

    fun release()

    fun onStateChanged(action: (state: PlayerState) -> Unit)

}