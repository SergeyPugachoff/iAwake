package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository

class GetProgramTracks(private val programsRepository: ProgramsRepository) {

    suspend operator fun invoke(programId: String): Result<List<TrackModel>> {
        return programsRepository.getProgramTracks(programId)
    }

}