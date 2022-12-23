package com.dvinov.myspaceapp.screen.apod

import com.dvinov.myspaceapp.screen.apod.model.ApodModel

data class ApodUiState(
    val apodData: LoadState<ApodModel>? = null,
    val imageDownloadState: LoadState<Unit>? = LoadState.Success()
)

sealed class LoadState<T : Any> {
    class Success<T : Any>(val data: T? = null) : LoadState<T>()
    class Error<T : Any>(val code: Int? = null, val message: String? = null) : LoadState<T>()
    class Loading<T : Any> : LoadState<T>()
}
