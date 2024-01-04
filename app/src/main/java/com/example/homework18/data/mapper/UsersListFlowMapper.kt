package com.example.homework18.data.mapper

import com.example.homework18.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, DomainType> Flow<Resource<List<T>>>.mapListToDomain(mapper: (T) -> DomainType): Flow<Resource<List<DomainType>>> {
    return map { resource ->
        when (resource) {
            is Resource.Success -> Resource.Success(resource.data.map(mapper))
            is Resource.Error -> Resource.Error(resource.errorMessage)
            is Resource.Loading -> Resource.Loading()
        }
    }
}