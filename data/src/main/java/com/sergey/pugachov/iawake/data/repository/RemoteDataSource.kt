package com.sergey.pugachov.iawake.data.repository

import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel

interface RemoteDataSource {

    suspend fun getPrograms(): List<ProgramModel>

}