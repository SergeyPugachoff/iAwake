package com.sergey.pugachov.iawake.data.db

import com.sergey.pugachov.iawake.data.db.entity.ProgramEntity
import com.sergey.pugachov.iawake.data.db.entity.TrackEntity
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