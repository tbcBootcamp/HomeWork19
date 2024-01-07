package com.example.hw18.data.dtoMapper

import com.example.hw18.data.dto.UsersDto
import com.example.hw18.domain.model.UserDomainModel
import com.example.hw18.utils.orZero

object UserMapper {
    fun UsersDto.mapDtoToDomain() =
        UserDomainModel(
            id.orZero(),
            email.orEmpty(),
            firstName.orEmpty(),
            lastName.orEmpty(),
            avatar.orEmpty()
        )
}