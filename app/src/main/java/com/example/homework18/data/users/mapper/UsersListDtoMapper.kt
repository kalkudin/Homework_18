package com.example.homework18.data.users.mapper

import com.example.homework18.data.users.dto.UsersListDto
import com.example.homework18.domain.users.model.UsersList

fun UsersListDto.toDomain() : UsersList{
    return UsersList(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}