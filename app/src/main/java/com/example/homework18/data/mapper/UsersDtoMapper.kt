package com.example.homework18.data.mapper

import com.example.homework18.data.dto.UserDto
import com.example.homework18.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}