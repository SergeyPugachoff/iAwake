package com.sergey.pugachov.iawake.ui.tracks.model

import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

data class TracksUiModel(
    val id: String,
    val title: String,
    val url: String,
    val state: State = State.Undefined
) {

    constructor(trackModel: TrackModel) : this(
        trackModel.id,
        trackModel.title,
        trackModel.url,
        State.Undefined
    )

    fun isPlaying(): Boolean =
        state == State.Loading || state == State.Playing

    fun isSame(other: TracksUiModel?): Boolean =
        other !== null && this.id == other.id

    enum class State {
        Loading, Playing, Paused, Stopped, Undefined
    }
}