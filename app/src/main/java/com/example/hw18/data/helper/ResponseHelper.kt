package com.example.hw18.data.helper

import com.example.hw18.utils.Resource
import retrofit2.Response

fun <T, R> makeRequest(
    response: Response<T>,
    map: (T) -> R
): Resource<R> {
    return try {
        if (response.isSuccessful) {
            Resource.Success(response.body()?.let { map(it) })
        } else {
            Resource.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        Resource.Error(e.message)
    }
}