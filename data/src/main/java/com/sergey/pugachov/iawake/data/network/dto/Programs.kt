package com.sergey.pugachov.iawake.data.network.dto

data class ProgramsResponse(
    val programs: List<Program>
)

data class Program(
    val id: String,
    val title: String? = null,
    val cover: Cover? = null,
    val tracks: List<Track>? = null
)

data class Cover(
    val resolutions: List<Resolution>? = null
)

data class Resolution(
    val url: String? = null,
    val size: Long? = null
)

data class Track(
    val key: String,
    val title: String? = null,
    val duration: Long? = null,
    val media: Media? = null
)

data class Media(
    val mp3: Mp3? = null
)

data class Mp3(
    val url: String? = null,
    val headURL: String? = null
)