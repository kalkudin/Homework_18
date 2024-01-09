package com.example.homework18.domain.users.usecase

import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import com.example.homework18.domain.users.repository.UsersListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val usersListRepository: UsersListRepository) {
    suspend operator fun invoke() : Flow<Resource<List<User>>> {
        return usersListRepository.getUsersList()
    }
}