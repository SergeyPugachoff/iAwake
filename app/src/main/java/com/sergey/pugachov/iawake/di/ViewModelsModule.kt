package com.sergey.pugachov.iawake.di

import com.sergey.pugachov.iawake.ui.programs.ProgramsViewModel
import com.sergey.pugachov.iawake.ui.tracks.TracksViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ProgramsViewModel(get()) }
    viewModel { (programId: String) -> TracksViewModel(programId, get(), get()) }
}