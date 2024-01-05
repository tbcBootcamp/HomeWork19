package com.example.hw18.domain.repository

import com.example.hw18.domain.model.User
import com.example.hw18.utils.Resource

interface UserRepository {
    suspend fun getUsers(): Resource<List<User>>
    suspend fun getUserDetails(userId: Int): Resource<User>
}