package com.sergey.pugachov.iawake.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.sergey.pugachov.iawake.BuildConfig
import com.sergey.pugachov.iawake.data.network.api.ProgramsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideRetrofit(get()) }
    single { provideApiService(get(), ProgramsApiService::class.java) }
}

interface NetworkSettings {
    val baseUrl: String
    val timeout: Long
}

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