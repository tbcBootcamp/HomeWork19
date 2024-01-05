package com.example.hw18.data.dtoMapper

import com.example.hw18.data.dto.DetailsDto
import com.example.hw18.domain.model.User
import com.example.hw18.utils.orZero

object UserDetailsMapper {
    fun DetailsDto.mapDtoToDomain(): User {
        with(this.data) {
            return User(
                this?.id.orZero(),
                this?.email.orEmpty(),
                this?.firstName.orEmpty(),
                this?.lastName.orEmpty(),
                this?.avatar.orEmpty()
            )
        }
    }
}