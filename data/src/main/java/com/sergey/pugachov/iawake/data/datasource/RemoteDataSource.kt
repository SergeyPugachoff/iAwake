package com.sergey.pugachov.iawake.data.datasource

import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel

interface RemoteDataSource {

    suspend fun getPrograms(): List<ProgramModel>

}