package com.cosmos.app.screen.apod.model

import java.time.LocalDate

data class ApodModel(
    val copyright: String? = null,
    val date: LocalDate? = null,
    val explanation: String? = null,
    val hdurl: String? = null,
    val media_type: String? = null,
    val service_version: String? = null,
    val title: String? = null,
    val url: String? = null,
)
