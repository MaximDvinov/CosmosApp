package com.cosmos.app.screen.random

import com.cosmos.app.ApiError
import com.cosmos.app.ApiException
import com.cosmos.app.ApiResult
import com.cosmos.app.ApiSuccess
import com.cosmos.app.network.NetworkManager
import com.cosmos.app.screen.random.model.ApodModel
import retrofit2.HttpException
import javax.inject.Inject

interface RandomRepository {
    suspend fun getApodDataRandom(): ApiResult<ApodModel>
}

class RandomRepositoryImpl @Inject constructor() : RandomRepository {
    override suspend fun getApodDataRandom(): ApiResult<ApodModel> {
        return try {
            val response = NetworkManager.getApodApi().getApodDataRandom()

            return ApiSuccess(response.getOrNull(0))

        } catch (e: HttpException) {
            ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            ApiException(e)
        }
    }


}