package com.sergey.pugachov.iawake.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracksUseCase
import com.sergey.pugachov.iawake.tools.player.PlayerState
import com.sergey.pugachov.iawake.tools.player.TrackPlayer
import com.sergey.pugachov.iawake.ui.tracks.model.TracksUiModel
import kotlinx.coroutines.launch

class TracksViewModel(
    private val getProgramTracksUseCase: GetProgramTracksUseCase,
    private val trackPlayer: TrackPlayer
) : ViewModel() {

    private lateinit var programId: String
    private lateinit var programCoverUrl: String
    private val _tracks = MutableLiveData<List<TracksUiModel>>()
    private val _selectedTrack = MutableLiveData<TracksUiModel>()

    val tracks: LiveData<List<TracksUiModel>> = _tracks
    val selectedTrack: LiveData<TracksUiModel> = _selectedTrack

    override fun onCleared() {
        trackPlayer.release()
        super.onCleared()
    }

    fun getTracks(programId: String, programCoverUrl: String){
        this.programId = programId
        this.programCoverUrl = programCoverUrl
        getTracks()
        trackPlayer.onStateChanged(::handlePlayerState)
    }

    fun play() {
        if (_selectedTrack.value == null && !_tracks.value.isNullOrEmpty()) {
            val track = _tracks.value?.first()
            play(track!!)
        }else{
            play(_selectedTrack.value!!)
        }
    }

    fun play(track: TracksUiModel) {
        val currentSelectedTrack = _selectedTrack.value
        _selectedTrack.value = track.copy(state = TracksUiModel.State.Loading)
        if (track.isSame(currentSelectedTrack) && currentSelectedTrack?.state == TracksUiModel.State.Paused) {
            trackPlayer.resume()
        } else {
            trackPlayer.play(track.title, programCoverUrl, track.url)
        }
    }

    fun pause() {
        _selectedTrack.value = _selectedTrack.value?.copy(state = TracksUiModel.State.Paused)
        trackPlayer.pause()
    }

    private fun getTracks() {
        viewModelScope.launch {
            val result = getProgramTracksUseCase(programId)
            _tracks.value = if (result is Result.Success) {
                result.data.map { TracksUiModel(it) }
            } else {
                emptyList()
            }
        }
    }

    private fun handlePlayerState(playerState: PlayerState) {
        val currentSelectedTrack = _selectedTrack.value

        val newState = when (playerState) {
            is PlayerState.Buffering -> TracksUiModel.State.Loading
            is PlayerState.Playing -> TracksUiModel.State.Playing
            is PlayerState.Paused -> TracksUiModel.State.Paused
            is PlayerState.Stopped -> TracksUiModel.State.Stopped
            else -> TracksUiModel.State.Undefined
        }

        if (currentSelectedTrack?.state != newState) {
            _selectedTrack.value = currentSelectedTrack?.copy(state = newState)
        }
    }
}