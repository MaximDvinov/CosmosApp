package com.dvinov.myspaceapp.di

import com.dvinov.myspaceapp.screen.apod.ApodRepository
import com.dvinov.myspaceapp.screen.apod.ApodRepositoryImpl
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