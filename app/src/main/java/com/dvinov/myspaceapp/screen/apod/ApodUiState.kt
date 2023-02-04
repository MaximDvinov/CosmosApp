package com.dvinov.myspaceapp.screen.apod

import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import java.time.LocalDate

data class ApodUiState(
    val apodData: LoadResult<ApodModel>? = null,
    val selectedDate: LocalDate? = null,
)

sealed class LoadResult<T : Any> {
    class Success<T : Any>(val data: T? = null) : LoadResult<T>()
    class Error<T : Any>(val code: Int? = null, val message: String? = null) : LoadResult<T>()
    class Loading<T : Any> : LoadResult<T>()
}
