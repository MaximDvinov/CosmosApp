package com.cosmos.app

import android.app.Application

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CosmosApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }

}