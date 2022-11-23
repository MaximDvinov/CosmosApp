package com.cosmos.app.di

import com.cosmos.app.screen.random.RandomRepository
import com.cosmos.app.screen.random.RandomRepositoryImpl
import com.cosmos.app.screen.random.RandomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<RandomRepository> { RandomRepositoryImpl() }
    viewModel { RandomViewModel(get()) }

}