package com.example.hw18.data.dto

import com.squareup.moshi.Json

data class DetailsDto(
    val data: DetailsData?,
    val support: DetailsSupport?
) {
    data class DetailsData(
        val id: Int?,
        val email: String?,
        @Json(name = "first_name")
        val firstName: String?,
        @Json(name = "last_name")
        val lastName: String?,
        val avatar: String?
    )
    data class DetailsSupport(
        val url: String?,
        val text: String?
    )
}