package com.example.homework18.data.service

import com.example.homework18.data.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailsService {
    @GET("/api/users/{id}")
    suspend fun getUserDetails(@Path("id") id: Int): Response<UserResponse>
}