package com.sergey.pugachov.iawake.ui.tracks.model

import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

data class SelectedTrack(
    val track: TrackModel,
    val isPlaying: Boolean = true
)