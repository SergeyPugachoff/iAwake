package com.sergey.pugachov.iawake.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergey.pugachov.iawake.databinding.ViewTracksListItemBinding
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel
import com.sergey.pugachov.iawake.ui.tracks.model.SelectedTrack

class TracksAdapter(
    private val onPlayClicked: (track: TrackModel) -> Unit
) : RecyclerView.Adapter<TracksAdapter.TrackHolder>() {

    private val items: MutableList<TrackModel> = mutableListOf()
    private var selectedTrack: SelectedTrack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding = ViewTracksListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrackHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = items[position]
        val isTrackCurrentlyPlayed =
            selectedTrack?.track?.id == track.id && selectedTrack?.isPlaying == true
        holder.bind(track, isTrackCurrentlyPlayed)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<TrackModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setSelectedTrack(selectedTrack: SelectedTrack) {
        this.selectedTrack = selectedTrack
        val index = items.indexOfFirst { selectedTrack.track.id == it.id }
        if (index >= 0) {
            items[index] = selectedTrack.track
            notifyDataSetChanged()
        }
    }

    inner class TrackHolder(
        private val binding: ViewTracksListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onPlayClicked(items[adapterPosition])
            }
        }

        fun bind(track: TrackModel, isPlaying: Boolean) {
            binding.playPauseButton.isChecked = isPlaying
            binding.trackTitle.text = track.title
        }
    }
}