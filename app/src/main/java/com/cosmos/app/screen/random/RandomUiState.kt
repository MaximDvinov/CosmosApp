package com.cosmos.app.screen.random

import com.cosmos.app.screen.random.model.ApodModel

data class RandomUiState(
    val isLoading: Boolean = false,
    val apodData: ApodModel? = null,
    val errorMessage: String? = null,
)

