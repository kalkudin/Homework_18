package com.example.homework18.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class HandleResponse {
    suspend fun <T : Any> handleApiCall(apiCall: suspend () -> Response<List<T>>): Flow<Resource<List<T>>> = flow {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
}