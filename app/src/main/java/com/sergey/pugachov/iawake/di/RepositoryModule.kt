package com.sergey.pugachov.iawake.di

import com.sergey.pugachov.iawake.data.db.LocalDataSourceImpl
import com.sergey.pugachov.iawake.data.network.RemoteDataSourceImpl
import com.sergey.pugachov.iawake.data.repository.LocalDataSource
import com.sergey.pugachov.iawake.data.repository.ProgramsRepositoryImpl
import com.sergey.pugachov.iawake.data.repository.RemoteDataSource
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<ProgramsRepository> { ProgramsRepositoryImpl(get(), get()) }
}