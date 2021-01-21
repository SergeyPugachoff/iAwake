package com.sergey.pugachov.iawake.domain.usecase

import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository

class GetPrograms(private val programsRepository: ProgramsRepository) {

    suspend operator fun invoke(): Result<List<ProgramModel>> {
        return programsRepository.getPrograms()
    }

}
