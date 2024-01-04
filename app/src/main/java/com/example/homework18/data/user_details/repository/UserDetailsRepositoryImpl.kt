package com.example.homework18.data.user_details.repository

import com.example.homework18.data.common.HandleResponse
import com.example.homework18.data.common.Resource
import com.example.homework18.data.mapper.toDomain
import com.example.homework18.data.service.UserDetailsService
import com.example.homework18.domain.model.User
import com.example.homework18.domain.user_details.repository.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(
    private val userDetailsService: UserDetailsService
) : UserDetailsRepository {

    override suspend fun getUserDetails(userId: Int): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val response = userDetailsService.getUserDetails(userId)
            if (response.isSuccessful) {
                val userDto = response.body()?.data
                if (userDto != null) {
                    emit(Resource.Success(data = userDto.toDomain()))
                } else {
                    emit(Resource.Error(errorMessage = "Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                emit(Resource.Error(errorMessage = parseErrorBody(errorBody)))
            }
        } catch (e: IOException) {
            emit(Resource.Error(errorMessage = "Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = "An unexpected error occurred: ${e.message}"))
        }
    }

    private fun parseErrorBody(errorBody: String): String {
        return errorBody
    }
}