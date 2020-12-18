package com.sergey.pugachov.iawake.domain.model.programs

data class ProgramModel(
    val id : String,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val tracks: List<TrackModel>
)
