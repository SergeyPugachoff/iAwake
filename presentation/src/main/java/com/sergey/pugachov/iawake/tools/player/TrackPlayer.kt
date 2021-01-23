package com.sergey.pugachov.iawake.tools.player

import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

interface TrackPlayer {

    fun play(track: TrackModel)

    fun pause()

    fun resume()

    fun release()

    fun onStateChanged(action: (state: PlayerState) -> Unit)

}