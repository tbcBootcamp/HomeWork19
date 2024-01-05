package com.example.hw18.domain.useCase

import com.example.hw18.domain.model.User
import com.example.hw18.domain.repository.UserRepository
import com.example.hw18.utils.Resource
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: Int): Resource<User> = userRepository.getUserDetails(userId)
}