package com.sergey.pugachov.iawake.data.db

import com.sergey.pugachov.iawake.data.db.dao.ProgramDao
import com.sergey.pugachov.iawake.data.db.dao.TrackDao
import com.sergey.pugachov.iawake.data.db.entity.TrackEntity
import com.sergey.pugachov.iawake.data.repository.LocalDataSource
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel

class LocalDataSourceImpl(
    private val programDao: ProgramDao,
    private val trackDao: TrackDao
) : LocalDataSource {

    override suspend fun savePrograms(programs: List<ProgramModel>) {
        programDao.insertAll(programs.map(ProgramModel::toEntity))

        programs.forEach { program ->
            trackDao.insertAll(program.tracks.map { it.toEntity(program.id) })
        }

    }

    override suspend fun getTracks(programId: String): List<TrackModel> =
        trackDao.getTracksByProgramId(programId).map(TrackEntity::toModel)

}