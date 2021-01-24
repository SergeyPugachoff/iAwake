package com.sergey.pugachov.iawake.data.datasource

import com.sergey.pugachov.iawake.data.db.entity.ProgramEntity
import com.sergey.pugachov.iawake.data.db.entity.TrackEntity
import com.sergey.pugachov.iawake.data.network.dto.Program
import com.sergey.pugachov.iawake.data.network.dto.Resolution
import com.sergey.pugachov.iawake.data.network.dto.Track
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

fun ProgramModel.toEntity(): ProgramEntity =
    ProgramEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )

fun TrackModel.toEntity(programId: String): TrackEntity =
    TrackEntity(
        id = id,
        programId = programId,
        title = title,
        url = url,
        duration = duration
    )

fun TrackEntity.toModel(): TrackModel =
    TrackModel(
        id = id,
        title = title,
        url = url,
        duration = duration
    )

fun Program.toModel(): ProgramModel =
    ProgramModel(
        id = id,
        title = title ?: "",
        imageUrl = cover?.resolutions?.findLargeImageUrlOnNull() ?: "",
        thumbnailUrl = cover?.resolutions?.findThumbnailUrlOnNull() ?: "",
        tracks = tracks?.map(Track::toModel) ?: emptyList()
    )

fun List<Resolution>.findThumbnailUrlOnNull(): String? =
    filterNot { it.url.isNullOrBlank() || it.size ?: 0 < 100 }
        .minByOrNull { it.size ?: 0 }?.url

fun List<Resolution>.findLargeImageUrlOnNull(): String? =
    filterNot { it.url.isNullOrBlank() }
        .maxByOrNull { it.size ?: 0 }?.url

fun Track.toModel(): TrackModel =
    TrackModel(
        id = key,
        title = title ?: "",
        url = media?.mp3?.url ?: "",
        duration = duration ?: 0
    )