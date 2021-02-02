package com.sergey.pugachov.iawake.data.di

import android.content.Context
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.sergey.pugachov.iawake.data.BuildConfig
import com.sergey.pugachov.iawake.data.db.DataBase
import com.sergey.pugachov.iawake.data.datasource.LocalDataSourceImpl
import com.sergey.pugachov.iawake.data.db.dao.ProgramDao
import com.sergey.pugachov.iawake.data.db.dao.TrackDao
import com.sergey.pugachov.iawake.data.datasource.RemoteDataSourceImpl
import com.sergey.pugachov.iawake.data.network.api.ProgramsApiService
import com.sergey.pugachov.iawake.data.datasource.LocalDataSource
import com.sergey.pugachov.iawake.data.repository.ProgramsRepositoryImpl
import com.sergey.pugachov.iawake.data.datasource.RemoteDataSource
import com.sergey.pugachov.iawake.data.repository.AudioSettingsRepositoryImpl
import com.sergey.pugachov.iawake.domain.repository.AudioSettingsRepository
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface DbSettings {
    val dbName : String
}

interface NetworkSettings {
    val baseUrl: String
    val timeout: Long
}

val dataModule = module {
    single { provideDataBase(androidApplication(), get()) }
    single { provideProgramDao(get()) }
    single { provideTrackDao(get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get(), ProgramsApiService::class.java) }
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<ProgramsRepository> { ProgramsRepositoryImpl(get(), get()) }
    single<AudioSettingsRepository> { AudioSettingsRepositoryImpl(androidApplication()) }
}

private fun provideDataBase(context: Context, dbSettings: DbSettings): DataBase =
    Room.databaseBuilder(context, DataBase::class.java, dbSettings.dbName).build()

private fun provideProgramDao(dataBase: DataBase): ProgramDao = dataBase.programDao()

private fun provideTrackDao(dataBase: DataBase): TrackDao = dataBase.trackDao()

private val converterFactory: Converter.Factory = GsonConverterFactory.create(Gson())

private fun buildOkHttpClient(timeout: Long): OkHttpClient =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            addInterceptor(StethoInterceptor())
        }
    }.connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .build()

private fun provideRetrofit(networkSettings: NetworkSettings): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl(networkSettings.baseUrl)
        .client(buildOkHttpClient(networkSettings.timeout))
        .build()

private fun <T> provideApiService(retrofit: Retrofit, serviceClass: Class<T>): T =
    retrofit.create(serviceClass)