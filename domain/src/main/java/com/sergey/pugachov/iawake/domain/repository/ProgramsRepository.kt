package com.sergey.pugachov.iawake.domain.repository

import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

interface ProgramsRepository {

    suspend fun getPrograms(): Result<List<ProgramModel>>

    suspend fun getProgramTracks(programId: String): Result<List<TrackModel>>

}