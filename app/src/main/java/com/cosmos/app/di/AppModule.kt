package com.cosmos.app.di

import com.cosmos.app.screen.random.RandomRepository
import com.cosmos.app.screen.random.RandomRepositoryImpl
import com.cosmos.app.screen.random.RandomViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AppModule {
    @Binds
    fun bindsRandomScreenRepository(
        randomRepository: RandomRepositoryImpl
    ): RandomRepository
}