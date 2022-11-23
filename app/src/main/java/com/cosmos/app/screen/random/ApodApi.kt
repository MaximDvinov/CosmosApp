package com.cosmos.app.screen.random

import com.cosmos.app.Constant.NASA_KEY
import com.cosmos.app.screen.random.model.ApodModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {
    @GET("/planetary/apod?")
    suspend fun getApodDataRandom(
        @Query("api_key") apiKey: String = NASA_KEY,
        @Query("count") count: Int = 1
    ): List<ApodModel>
}
