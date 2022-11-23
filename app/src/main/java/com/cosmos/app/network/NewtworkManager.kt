package com.cosmos.app.network

import com.cosmos.app.screen.random.RandomApi
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    private const val RANDOM_URL = "https://api.nasa.gov/"

    private val jsonObjectMapper: ObjectMapper = ObjectMapper()
        .findAndRegisterModules()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

    fun getRandomApi(): RandomApi {
        val appHttpClient: OkHttpClient by lazy {
            OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        val api: RandomApi by lazy {
            Retrofit.Builder()
                .client(appHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RANDOM_URL)
                .build()
                .create(RandomApi::class.java)
        }

        return api
    }
}