package com.sergey.pugachov.iawake.domain.di

import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracksUseCase
import com.sergey.pugachov.iawake.domain.usecase.GetProgramsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProgramsUseCase(get()) }
    factory { GetProgramTracksUseCase(get()) }
}