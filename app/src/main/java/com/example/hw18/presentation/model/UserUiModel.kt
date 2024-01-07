package com.example.hw18.presentation.model

data class UserUiModel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    var isSelected: Boolean = false
)
