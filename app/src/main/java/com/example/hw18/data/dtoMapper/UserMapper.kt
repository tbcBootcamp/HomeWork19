package com.example.hw18.data.dtoMapper

import com.example.hw18.data.dto.UsersDto
import com.example.hw18.domain.model.User
import com.example.hw18.utils.orZero

object UserMapper {
    fun UsersDto.mapDtoToDomain() =
        User(
            id.orZero(),
            email.orEmpty(),
            firstName.orEmpty(),
            lastName.orEmpty(),
            avatar.orEmpty()
        )
}