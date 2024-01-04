package com.example.homework18.domain.users.repository

import com.example.homework18.data.common.Resource
import com.example.homework18.domain.users.model.UsersList
import kotlinx.coroutines.flow.Flow

interface UsersListRepository{
    suspend fun getUsersList() : Flow<Resource<List<UsersList>>>
}