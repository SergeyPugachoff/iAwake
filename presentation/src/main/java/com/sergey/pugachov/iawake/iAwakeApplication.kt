package com.sergey.pugachov.iawake

import android.app.Application
import com.facebook.stetho.Stetho
import com.sergey.pugachov.iawake.data.di.dataModule
import com.sergey.pugachov.iawake.di.modules.applicationModule
import com.sergey.pugachov.iawake.di.modules.viewModelsModule
import com.sergey.pugachov.iawake.domain.di.domainModule
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
                    domainModule,
                    dataModule,
                    viewModelsModule
                )
            )
        }

        Stetho.initializeWithDefaults(this)
    }
}