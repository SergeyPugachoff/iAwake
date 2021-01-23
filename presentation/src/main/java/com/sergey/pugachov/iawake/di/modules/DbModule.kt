package com.sergey.pugachov.iawake.di.modules

import android.content.Context
import androidx.room.Room
import com.sergey.pugachov.iawake.data.db.DataBase
import com.sergey.pugachov.iawake.data.db.dao.ProgramDao
import com.sergey.pugachov.iawake.data.db.dao.TrackDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single { provideDataBase(androidApplication(), get()) }
    single { provideProgramDao(get()) }
    single { provideTrackDao(get()) }
}

interface DbSettings {
    val dbName : String
}

private fun provideProgramDao(dataBase: DataBase): ProgramDao = dataBase.programDao()

private fun provideTrackDao(dataBase: DataBase): TrackDao = dataBase.trackDao()

private fun provideDataBase(context: Context, dbSettings: DbSettings): DataBase =
    Room.databaseBuilder(context, DataBase::class.java, dbSettings.dbName).build()