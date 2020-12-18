package com.sergey.pugachov.iawake.di

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
    single { provideRetrofit() }
    factory { provideApiService(get(), ProgramsApiService::class.java) }
}


private const val BASE_URL = "https://api.iawaketechnologies.com/"
private val converterFactory: Converter.Factory = GsonConverterFactory.create(Gson())
private val okHttpClient: OkHttpClient =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            addInterceptor(StethoInterceptor())
        }
    }.connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

private fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun <T> provideApiService(retrofit: Retrofit, serviceClass: Class<T>): T =
    retrofit.create(serviceClass)