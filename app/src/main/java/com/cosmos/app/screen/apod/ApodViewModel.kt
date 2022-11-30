package com.cosmos.app.screen.apod

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmos.app.ApiError
import com.cosmos.app.ApiException
import com.cosmos.app.ApiSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {
    var uiState by mutableStateOf(ApodUiState())
        private set

    fun getApodDataRandom() {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            uiState = when (val result = repository.getApodDataRandom()) {
                is ApiError -> {
                    Log.e("getRandomImage", result.message.toString())
                    uiState.copy(isLoading = false, errorMessage = result.message)

                }
                is ApiException -> {
                    Log.e("getRandomImage", result.e.message.toString())
                    uiState.copy(isLoading = false, errorMessage = "Неизвестная ошибка")
                }
                is ApiSuccess -> {
                    Log.e("getRandomImage", "Success")
                    uiState.copy(isLoading = false, apodData = result.data)
                }
            }
        }
    }
}