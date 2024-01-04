package com.example.homework18.domain.user_details.repository

import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {
    suspend fun getUserDetails(userId : Int) : Flow<Resource<User>>
}