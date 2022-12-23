package com.dvinov.myspaceapp.screen.apod

import com.dvinov.myspaceapp.ApiError
import com.dvinov.myspaceapp.ApiException
import com.dvinov.myspaceapp.ApiResult
import com.dvinov.myspaceapp.ApiSuccess
import com.dvinov.myspaceapp.network.NetworkManager
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

interface ApodRepository {
    suspend fun getApodDataRandom(): ApiResult<ApodModel>
    suspend fun getApodToday(): ApiResult<ApodModel>
    suspend fun getApodDate(date: LocalDate): ApiResult<ApodModel>
}

class ApodRepositoryImpl @Inject constructor() : ApodRepository {
    override suspend fun getApodDataRandom(): ApiResult<ApodModel> {
        return try {
            val response = NetworkManager.getApodApi().getApodDataRandom()

            return ApiSuccess(response.getOrNull(0))

        } catch (e: HttpException) {
            ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiException(e)
        }
    }

    override suspend fun getApodToday(): ApiResult<ApodModel> {
        return try {
            val response = NetworkManager.getApodApi().getApodToday()

            return ApiSuccess(response)

        } catch (e: HttpException) {
            e.printStackTrace()
            ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiException(e)
        }
    }

    override suspend fun getApodDate(date: LocalDate): ApiResult<ApodModel> {
        return try {
            val response = NetworkManager.getApodApi().getApodDate(date = date)

            return ApiSuccess(response)

        } catch (e: HttpException) {
            e.printStackTrace()
            ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiException(e)
        }
    }
}