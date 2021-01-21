package com.sergey.pugachov.iawake.di

import com.sergey.pugachov.iawake.data.db.LocalDataSourceImpl
import com.sergey.pugachov.iawake.data.network.RemoteDataSourceImpl
import com.sergey.pugachov.iawake.data.repository.LocalDataSource
import com.sergey.pugachov.iawake.data.repository.ProgramsRepositoryImpl
import com.sergey.pugachov.iawake.data.repository.RemoteDataSource
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracks
import com.sergey.pugachov.iawake.domain.usecase.GetPrograms
import org.koin.dsl.module

val repositoryModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<ProgramsRepository> { ProgramsRepositoryImpl(get(), get()) }
}