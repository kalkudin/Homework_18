package com.example.homework18.data.users.repository

import android.net.http.HttpException
import com.example.homework18.data.common.Resource
import com.example.homework18.data.users.mapper.toDomain
import com.example.homework18.data.service.UsersService
import com.example.homework18.domain.users.model.UsersList
import com.example.homework18.domain.users.repository.UsersListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class UsersListRepositoryImpl @Inject constructor(private val usersService: UsersService): UsersListRepository {
    override suspend fun getUsersList(): Flow<Resource<List<UsersList>>> {
        return flow {
            val response = usersService.getUsers()
            try {
                if (response.isSuccessful) {
                    val usersListDto = response.body() ?: emptyList()
                    val usersList = usersListDto.map { it.toDomain() }
                    emit(Resource.Success(data = usersList))
                } else {
                    emit(Resource.Error(errorMessage = "Failure While Fetching Data"))
                }
            } catch (e: IOException) {
                emit(Resource.Error(errorMessage = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(Resource.Error(errorMessage = e.message ?: "Please check your network connection"))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = "Something went wrong"))
            }
        }
    }
}