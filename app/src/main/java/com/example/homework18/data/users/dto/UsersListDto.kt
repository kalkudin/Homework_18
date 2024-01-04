package com.example.homework18.data.users.dto

import com.squareup.moshi.Json

data class UsersListDto(
    val id : Int,
    val email : String,
    @Json(name = "first_name")
    val firstName : String,
    @Json(name = "last_name")
    val lastName : String,
    val avatar : String
)