package com.example.hw18.presentation.toUiMapper

import com.example.hw18.domain.model.UserDomainModel
import com.example.hw18.presentation.model.UserUiModel

object UserUiMapper {
    fun UserDomainModel.mapToUiModel() =
        UserUiModel(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar
        )
}