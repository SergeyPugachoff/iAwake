package com.sergey.pugachov.iawake.data.network.api

import com.sergey.pugachov.iawake.data.network.dto.ProgramsResponse
import retrofit2.http.GET

interface ProgramsApiService {
    @GET("/api/v2/media-library/free")
    suspend fun getPrograms(): ProgramsResponse
}