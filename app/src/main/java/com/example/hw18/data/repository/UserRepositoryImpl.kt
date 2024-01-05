package com.example.hw18.data.repository

import com.example.hw18.data.dtoMapper.UserDetailsMapper.mapDtoToDomain
import com.example.hw18.data.dtoMapper.UserMapper.mapDtoToDomain
import com.example.hw18.data.network.UserDetailsService
import com.example.hw18.data.network.UsersService
import com.example.hw18.domain.model.User
import com.example.hw18.domain.repository.UserRepository
import com.example.hw18.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UsersService,
    private val userDetailsService: UserDetailsService
) : UserRepository {

    override suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = userService.getUsers()
            if (response.isSuccessful) {
                Resource.Success(response.body()?.map { it.mapDtoToDomain() } ?: emptyList())
            } else {
                Resource.Error(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getUserDetails(userId: Int): Resource<User> {
        return try {
            val response = userDetailsService.getUser(userId)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.mapDtoToDomain())
            } else {
                Resource.Error(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}