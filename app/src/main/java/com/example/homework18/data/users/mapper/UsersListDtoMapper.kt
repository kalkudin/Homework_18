package com.example.homework18.data.users.mapper

import com.example.homework18.data.users.dto.UsersListDto
import com.example.homework18.domain.model.User

fun UsersListDto.toDomain() : User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}