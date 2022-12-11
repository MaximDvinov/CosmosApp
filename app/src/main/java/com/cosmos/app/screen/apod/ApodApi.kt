package com.cosmos.app.screen.apod

import com.cosmos.app.Constant.NASA_KEY
import com.cosmos.app.screen.apod.model.ApodModel
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface ApodApi {
    @GET("/planetary/apod?")
    suspend fun getApodDataRandom(
        @Query("api_key") apiKey: String = NASA_KEY,
        @Query("count") count: Int = 1
    ): List<ApodModel>

    @GET("/planetary/apod?")
    suspend fun getApodToday(
        @Query("api_key") apiKey: String = NASA_KEY,
    ): ApodModel

    @GET("/planetary/apod?")
    suspend fun getApodDate(
        @Query("api_key") apiKey: String = NASA_KEY,
        @Query("date") date: LocalDate,
    ): ApodModel
}
