package com.sergey.pugachov.iawake.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel
import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracksUseCase
import com.sergey.pugachov.iawake.tools.player.PlayerState
import com.sergey.pugachov.iawake.tools.player.TrackPlayer
import com.sergey.pugachov.iawake.ui.tracks.model.SelectedTrack
import kotlinx.coroutines.launch

class TracksViewModel(
    private val programId: String,
    private val programCoverUrl: String,
    private val getProgramTracksUseCase: GetProgramTracksUseCase,
    private val trackPlayer: TrackPlayer
) : ViewModel() {

    private val _tracks = MutableLiveData<List<TrackModel>>()
    private val _selectedTrack = MutableLiveData<SelectedTrack>()

    val tracks: LiveData<List<TrackModel>> = _tracks
    val selectedTrack: LiveData<SelectedTrack> = _selectedTrack

    init {
        getTracks()
        trackPlayer.onStateChanged(::handlePlayerState)
    }

    override fun onCleared() {
        trackPlayer.release()
        super.onCleared()
    }

    fun toggleTrackSelection(track: TrackModel) {
        val currentTrack = _selectedTrack.value
        when {
            currentTrack?.track != track -> {
                _selectedTrack.value = SelectedTrack(track)
                trackPlayer.play(track.title, programCoverUrl, track.url)
            }
            currentTrack.isPlaying -> {
                _selectedTrack.value = currentTrack.copy(isPlaying = false)
                trackPlayer.pause()
            }
            else -> {
                _selectedTrack.value = currentTrack.copy(isPlaying = false)
                trackPlayer.resume()
            }
        }
    }

    private fun getTracks() {
        viewModelScope.launch {
            val result = getProgramTracksUseCase(programId)
            _tracks.value = if (result is Result.Success) result.data else emptyList()
        }
    }

    private fun handlePlayerState(playerState: PlayerState) {
        val currentTrack = _selectedTrack.value
        _selectedTrack.value = currentTrack?.copy(isPlaying = playerState == PlayerState.Playing)
    }
}