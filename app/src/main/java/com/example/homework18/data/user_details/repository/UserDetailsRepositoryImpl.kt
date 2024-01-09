package com.example.homework18.data.user_details.repository

import com.example.homework18.data.common.HandleResponse
import com.example.homework18.data.common.Resource
import com.example.homework18.data.mapper.mapResource
import com.example.homework18.data.mapper.toDomain
import com.example.homework18.data.service.UserDetailsService
import com.example.homework18.domain.model.User
import com.example.homework18.domain.user_details.repository.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(
    private val userDetailsService: UserDetailsService,
    private val handleResponse: HandleResponse
) : UserDetailsRepository {

    override suspend fun getUserDetails(userId: Int): Flow<Resource<User>> {
        return handleResponse.handleApiCall { userDetailsService.getUserDetails(userId) }
            .mapResource { userResponse ->
                userResponse.data.toDomain()
            }
    }
}