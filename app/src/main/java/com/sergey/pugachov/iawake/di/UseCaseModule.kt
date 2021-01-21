package com.sergey.pugachov.iawake.di

import com.sergey.pugachov.iawake.domain.usecase.GetProgramTracks
import com.sergey.pugachov.iawake.domain.usecase.GetPrograms
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPrograms(get()) }
    factory { GetProgramTracks(get()) }
}