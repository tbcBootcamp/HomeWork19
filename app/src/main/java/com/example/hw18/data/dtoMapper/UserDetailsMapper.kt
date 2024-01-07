package com.example.hw18.data.dtoMapper

import com.example.hw18.data.dto.DetailsDto
import com.example.hw18.domain.model.UserDomainModel
import com.example.hw18.utils.orZero

object UserDetailsMapper {
    fun DetailsDto.mapDtoToDomain(): UserDomainModel {
        with(this.data) {
            return UserDomainModel(
                this?.id.orZero(),
                this?.email.orEmpty(),
                this?.firstName.orEmpty(),
                this?.lastName.orEmpty(),
                this?.avatar.orEmpty()
            )
        }
    }
}