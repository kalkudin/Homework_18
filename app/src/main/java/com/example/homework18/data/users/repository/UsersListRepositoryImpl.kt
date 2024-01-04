package com.example.homework18.data.users.repository

import com.example.homework18.data.common.HandleResponse
import com.example.homework18.data.common.Resource
import com.example.homework18.data.service.UsersService
import com.example.homework18.data.users.mapper.mapToDomain
import com.example.homework18.data.users.mapper.toDomain
import com.example.homework18.domain.model.User
import com.example.homework18.domain.users.repository.UsersListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersListRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
    private val handleResponse: HandleResponse
) : UsersListRepository {

    override suspend fun getUsersList(): Flow<Resource<List<User>>> {
        return handleResponse.handleApiCall { usersService.getUsers() }
            .mapToDomain { it.toDomain() }
    }
}