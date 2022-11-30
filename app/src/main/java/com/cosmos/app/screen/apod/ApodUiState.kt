package com.cosmos.app.screen.apod

import com.cosmos.app.screen.apod.model.ApodModel

data class ApodUiState(
    val isLoading: Boolean = false,
    val apodData: ApodModel? = null,
    val errorMessage: String? = null,
)

