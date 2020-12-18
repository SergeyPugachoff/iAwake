package com.sergey.pugachov.iawake.data.repository

import com.sergey.pugachov.iawake.domain.model.common.Error
import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.model.programs.TrackModel
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import retrofit2.HttpException

class ProgramsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProgramsRepository {

    override suspend fun getPrograms(): Result<List<ProgramModel>> =
        try {
            val remotePrograms = remoteDataSource.getPrograms()
            localDataSource.savePrograms(remotePrograms)
            Result.Success(remotePrograms)
        } catch (httpException: HttpException) {
            Result.Failure(Error.Http)
        } catch (anyException: Exception) {
            Result.Failure(Error.Unknown(anyException))
        }

    override suspend fun getProgramTracks(programId: String): Result<List<TrackModel>> =
        try {
            Result.Success(localDataSource.getTracks(programId))
        } catch (anyException: Exception) {
            Result.Failure(Error.Unknown(anyException))
        }

}