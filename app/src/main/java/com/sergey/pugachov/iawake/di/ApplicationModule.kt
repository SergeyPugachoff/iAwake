package com.sergey.pugachov.iawake.di

import com.sergey.pugachov.iawake.playback.TrackPlayer
import com.sergey.pugachov.iawake.playback.TrackTrackPlayerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    factory<TrackPlayer> { TrackTrackPlayerImpl(androidContext()) }
}