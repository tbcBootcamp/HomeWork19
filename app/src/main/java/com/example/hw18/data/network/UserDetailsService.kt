package com.example.hw18.data.network

import com.example.hw18.data.dto.DetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailsService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<DetailsDto>
}