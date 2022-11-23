package com.cosmos.app

import android.app.Application
import com.cosmos.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CosmosApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@CosmosApp)
            modules(appModule)
        }
    }

}