package com.sergey.pugachov.iawake.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import com.sergey.pugachov.iawake.playback.TrackPlayer
import com.sergey.pugachov.iawake.playback.PlayerState
import com.sergey.pugachov.iawake.ui.tracks.model.SelectedTrack
import kotlinx.coroutines.launch

class TracksViewModel(
    private val programId: String,
    private val repository: ProgramsRepository,
    private val trackPlayer: TrackPlayer
) : ViewModel() {

    private val _tracks = MutableLiveData<List<TrackModel>>()
    val tracks: LiveData<List<TrackModel>> = _tracks

    private val _selectedTrack = MutableLiveData<SelectedTrack>()
    val selectedTrack: LiveData<SelectedTrack> = _selectedTrack

    init {
        getTracks()
        trackPlayer.onStateChanged(::handlePlayerState)
    }

    fun toggleTrackSelection(track: TrackModel) {
        val currentTrack = _selectedTrack.value
        when {
            currentTrack?.track != track -> {
                _selectedTrack.value = SelectedTrack(track)
                trackPlayer.play(track)
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

    override fun onCleared() {
        trackPlayer.release()
        super.onCleared()
    }

    private fun getTracks() {
        viewModelScope.launch {
            val result = repository.getProgramTracks(programId)
            _tracks.value = if (result is Result.Success) result.data else emptyList()
        }
    }

    private fun handlePlayerState(playerState: PlayerState) {
        val currentTrack = _selectedTrack.value
        _selectedTrack.value = currentTrack?.copy(isPlaying = playerState == PlayerState.Playing)
    }
}