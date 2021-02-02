package com.sergey.pugachov.iawake.di.modules

import com.sergey.pugachov.iawake.ui.programs.ProgramsViewModel
import com.sergey.pugachov.iawake.ui.tracks.TracksViewModel
import com.sergey.pugachov.iawake.ui.tracks.audiosettings.AudioSettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ProgramsViewModel(get()) }
    viewModel { (programId: String, programCoverUrl: String) ->
        TracksViewModel(
            programId,
            programCoverUrl,
            get(),
            get()
        )
    }
    viewModel { AudioSettingsViewModel(get(), get(), get(), get()) }
}