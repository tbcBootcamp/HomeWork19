package com.example.hw18.domain.useCase

import com.example.hw18.domain.model.UserDomainModel
import com.example.hw18.domain.repository.UserRepository
import com.example.hw18.utils.Resource
import javax.inject.Inject

class GetUsersUseCase @Inject constructor (private val userRepository: UserRepository) {
    suspend operator fun invoke(): Resource<List<UserDomainModel>> = userRepository.getUsers()
}