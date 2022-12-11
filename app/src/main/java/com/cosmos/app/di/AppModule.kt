package com.cosmos.app.di

import com.cosmos.app.screen.apod.ApodRepository
import com.cosmos.app.screen.apod.ApodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AppModule {
    @Binds
    fun bindsApodRepository(
        apodRepository: ApodRepositoryImpl
    ): ApodRepository
}