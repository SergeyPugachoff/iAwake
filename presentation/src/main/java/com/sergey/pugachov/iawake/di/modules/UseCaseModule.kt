package com.sergey.pugachov.iawake.di.modules

import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracksUseCase
import com.sergey.pugachov.iawake.domain.usecase.GetProgramsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProgramsUseCase(get()) }
    factory { GetProgramTracksUseCase(get()) }
}