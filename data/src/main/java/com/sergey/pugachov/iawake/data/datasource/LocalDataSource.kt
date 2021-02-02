package com.sergey.pugachov.iawake.data.datasource

import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

interface LocalDataSource {

    suspend fun savePrograms(programs: List<ProgramModel>)

    suspend fun getTracks(programId: String) : List<TrackModel>

}