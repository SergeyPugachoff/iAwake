package com.sergey.pugachov.iawake.data.network

import com.sergey.pugachov.iawake.data.network.api.ProgramsApiService
import com.sergey.pugachov.iawake.data.network.dto.Program
import com.sergey.pugachov.iawake.data.repository.RemoteDataSource
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel

class RemoteDataSourceImpl(
    private val programsApiService: ProgramsApiService
) : RemoteDataSource {

    override suspend fun getPrograms(): List<ProgramModel> =
        programsApiService.getPrograms().programs.map(Program::toModel)

}