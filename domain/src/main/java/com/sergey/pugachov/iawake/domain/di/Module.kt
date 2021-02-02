package com.sergey.pugachov.iawake.domain.di

import com.sergey.pugachov.iawake.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetProgramsUseCase(get()) }
    factory { GetProgramTracksUseCase(get()) }
    factory { GetCurrentVolumeUseCase(get()) }
    factory { SetVolumeUseCase(get()) }
    factory { IsSpeakerPhoneOnUseCase(get()) }
    factory { SetSpeakerPhoneOnUseCase(get()) }
}