package com.sergey.pugachov.iawake

import android.app.Application
import com.facebook.stetho.Stetho
import com.sergey.pugachov.iawake.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                modules = listOf(
                    applicationModule,
                    dbModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelsModule
                )
            )
        }

        Stetho.initializeWithDefaults(this)
    }
}