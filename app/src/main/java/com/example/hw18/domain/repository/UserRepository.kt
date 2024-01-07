package com.example.hw18.domain.repository

import com.example.hw18.domain.model.UserDomainModel
import com.example.hw18.utils.Resource

interface UserRepository {
    suspend fun getUsers(): Resource<List<UserDomainModel>>
    suspend fun getUserDetails(userId: Int): Resource<UserDomainModel>
}