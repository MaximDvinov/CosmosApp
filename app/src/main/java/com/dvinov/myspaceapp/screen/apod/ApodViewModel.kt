package com.dvinov.myspaceapp.screen.apod

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvinov.myspaceapp.ApiError
import com.dvinov.myspaceapp.ApiException
import com.dvinov.myspaceapp.ApiResult
import com.dvinov.myspaceapp.ApiSuccess
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject


private const val TAG = "ApodViewModel"

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ApodUiState(null, LocalDate.now()))
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    fun getApodDataRandom() {
        _uiState.value = _uiState.value.copy(apodData = LoadResult.Loading())

        viewModelScope.launch {
            _uiState.value = getUiState(repository.getApodDataRandom())
        }
    }

    fun getApodToday() {
        _uiState.value = _uiState.value.copy(apodData = LoadResult.Loading())

        viewModelScope.launch {
            _uiState.value = getUiState(repository.getApodToday())
        }
    }

    fun selectDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun getApodDate() {
        _uiState.value = _uiState.value.copy(apodData = LoadResult.Loading())

        viewModelScope.launch {
            if (_uiState.value.selectedDate != null) {
                _uiState.value = getUiState(repository.getApodDate(_uiState.value.selectedDate!!))
            }
        }
    }

    fun changeShowDialog(isShowDialog: Boolean){
        _uiState.value = _uiState.value.copy(isShowDialog = isShowDialog)
    }

    private fun getUiState(result: ApiResult<ApodModel>) = when (result) {
        is ApiError -> {
            Log.e(TAG, "getUiState: ${result.message}")

            _uiState.value.copy(
                apodData = LoadResult.Error(
                    code = result.code, message = result.message
                )
            )
        }
        is ApiException -> {
            Log.e(TAG, "getUiState: ${result.e.message}", result.e)

            if (result.e is UnknownHostException) {
                _uiState.value.copy(apodData = LoadResult.Error(message = "No internet connection"))
            } else {
                _uiState.value.copy(apodData = LoadResult.Error(message = result.e.message.toString()))
            }
        }
        is ApiSuccess -> {
            Log.i(TAG, "getUiState: success")
            _uiState.value.copy(
                apodData = LoadResult.Success(result.data),
                selectedDate = result.data?.date
            )
        }
    }
}