package com.example.homework18.data.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HandleResponse @Inject constructor() {
    fun <T : Any> handleApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: throw NullPointerException("Response body is null")))
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
                Log.d("HandleResponse", "API call failed with error code: ${response.code()}")
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
}