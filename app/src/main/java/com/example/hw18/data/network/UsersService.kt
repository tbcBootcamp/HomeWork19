package com.example.hw18.data.network

import com.example.hw18.data.dto.UsersDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersService {
    @GET("7ec14eae-06bf-4f6d-86d2-ac1b9df5fe3d")
    suspend fun getUsers(): Response<List<UsersDto>>
}