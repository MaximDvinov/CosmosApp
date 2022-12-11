package com.cosmos.app.screen.apod

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmos.app.ApiError
import com.cosmos.app.ApiException
import com.cosmos.app.ApiResult
import com.cosmos.app.ApiSuccess
import com.cosmos.app.screen.apod.model.ApodModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {
    var uiState by mutableStateOf(ApodUiState(null))
        private set

    fun getApodDataRandom() {
        uiState = uiState.copy(apodData = LoadState.Loading())

        viewModelScope.launch {
            uiState = getUiState(repository.getApodDataRandom())
        }
    }

    fun getApodToday() {
        uiState = uiState.copy(apodData = LoadState.Loading())

        viewModelScope.launch {
            uiState = getUiState(repository.getApodToday())
        }
    }

    fun getApodDate(date: LocalDate) {
        uiState = uiState.copy(apodData = LoadState.Loading())

        viewModelScope.launch {
            uiState = getUiState(repository.getApodDate(date))
        }
    }

    private  fun getUiState(result: ApiResult<ApodModel>) = when (result) {
        is ApiError -> {
            Log.e("getApod", result.message.toString())
            uiState.copy(
                apodData = LoadState.Error(
                    code = result.code, message = result.message
                )
            )

        }
        is ApiException -> {
            Log.e("getApod", result.e.message.toString())
            if (result.e is UnknownHostException) {
                uiState.copy(apodData = LoadState.Error(message = "No internet connection"))
            } else {
                uiState.copy(apodData = LoadState.Error(message = result.e.message.toString()))
            }


        }
        is ApiSuccess -> {
            Log.e("getApod", "Success")
            uiState.copy(apodData = LoadState.Success(result.data))

        }
    }

    fun changeState(loadState: LoadState<Unit>) {
        uiState = uiState.copy(imageDownloadState = loadState)
    }

}