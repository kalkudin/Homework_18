package com.example.homework18.domain.user_details.usecase

import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import com.example.homework18.domain.user_details.repository.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsUseCase @Inject constructor(private val userDetailsRepository: UserDetailsRepository) {
    suspend operator fun invoke(userId: Int): Flow<Resource<User>> {
        return userDetailsRepository.getUserDetails(userId = userId)
    }
}