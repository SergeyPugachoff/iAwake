package com.sergey.pugachov.iawake

import android.app.Application
import com.facebook.stetho.Stetho
import com.sergey.pugachov.iawake.di.modules.applicationModule
import com.sergey.pugachov.iawake.di.modules.dbModule
import com.sergey.pugachov.iawake.di.modules.networkModule
import com.sergey.pugachov.iawake.di.modules.viewModelsModule
import com.sergey.pugachov.iawake.di.modules.repositoryModule
import com.sergey.pugachov.iawake.di.modules.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class iAwakeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@iAwakeApplication)
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