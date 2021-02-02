package com.sergey.pugachov.iawake.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergey.pugachov.iawake.databinding.ViewTracksListItemBinding
import com.sergey.pugachov.iawake.ui.tracks.model.TracksUiModel

class TracksAdapter(
    private val onItemClick: (track: TracksUiModel) -> Unit
) : RecyclerView.Adapter<TracksAdapter.TrackHolder>() {

    private val items: MutableList<TracksUiModel> = mutableListOf()
    private var selectedTrack: TracksUiModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding = ViewTracksListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrackHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = items[position]
        val isSelected = selectedTrack?.id == track.id
        holder.bind(track, isSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<TracksUiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setSelectedTrack(track: TracksUiModel) {
        val oldSelectedTrackId = selectedTrack?.id
        val newSelectedTrackId = track.id

        selectedTrack = track

        if (oldSelectedTrackId != null) {
            notifyTrackChanged(oldSelectedTrackId)
        }

        notifyTrackChanged(newSelectedTrackId)
    }

    private fun notifyTrackChanged(id: String) {
        val position = items.indexOfFirst { it.id == id }
        if (position >= 0) notifyItemChanged(position)
    }

    inner class TrackHolder(
        private val binding: ViewTracksListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }

        fun bind(track: TracksUiModel, isSelected: Boolean) {
            binding.trackTitle.text = track.title
            binding.trackTitle.isChecked = isSelected
        }
    }
}