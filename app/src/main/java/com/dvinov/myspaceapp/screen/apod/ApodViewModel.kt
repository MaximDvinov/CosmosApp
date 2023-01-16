package com.dvinov.myspaceapp.screen.apod

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvinov.myspaceapp.ApiError
import com.dvinov.myspaceapp.ApiException
import com.dvinov.myspaceapp.ApiResult
import com.dvinov.myspaceapp.ApiSuccess
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject


private const val TAG = "ApodViewModel"

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {
    var uiState by mutableStateOf(ApodUiState(null, LocalDate.now()))
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

    fun selectDate(date: LocalDate) {
        uiState = uiState.copy(selectedDate = date)
    }

    fun getApodDate() {
        uiState = uiState.copy(apodData = LoadState.Loading())

        viewModelScope.launch {
            if (uiState.selectedDate != null) {
                uiState = getUiState(repository.getApodDate(uiState.selectedDate!!))
            }
        }
    }

    private fun getUiState(result: ApiResult<ApodModel>) = when (result) {
        is ApiError -> {
            Log.e(TAG, "getUiState: ${result.message}")

            uiState.copy(
                apodData = LoadState.Error(
                    code = result.code, message = result.message
                )
            )
        }
        is ApiException -> {
            Log.e(TAG, "getUiState: ${result.e.message}", result.e)

            if (result.e is UnknownHostException) {
                uiState.copy(apodData = LoadState.Error(message = "No internet connection"))
            } else {
                uiState.copy(apodData = LoadState.Error(message = result.e.message.toString()))
            }
        }
        is ApiSuccess -> {
            Log.i(TAG, "getUiState: success")
            uiState.copy(apodData = LoadState.Success(result.data))

        }
    }
}