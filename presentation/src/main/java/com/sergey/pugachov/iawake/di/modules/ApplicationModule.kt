package com.sergey.pugachov.iawake.di.modules

import com.sergey.pugachov.iawake.di.configuration.Config
import com.sergey.pugachov.iawake.tools.player.TrackPlayer
import com.sergey.pugachov.iawake.tools.player.TrackTrackPlayerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single<NetworkSettings> { Config }
    single<DbSettings> { Config }
    factory<TrackPlayer> { TrackTrackPlayerImpl(androidContext()) }
}