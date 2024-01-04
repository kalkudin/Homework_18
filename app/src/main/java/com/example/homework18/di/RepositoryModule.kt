package com.example.homework18.di

import com.example.homework18.data.common.HandleResponse
import com.example.homework18.data.service.UserDetailsService
import com.example.homework18.data.users.repository.UsersListRepositoryImpl
import com.example.homework18.data.service.UsersService
import com.example.homework18.data.user_details.repository.UserDetailsRepositoryImpl
import com.example.homework18.domain.user_details.repository.UserDetailsRepository
import com.example.homework18.domain.users.repository.UsersListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        usersService: UsersService,
        handleResponse: HandleResponse
    ): UsersListRepository {
        return UsersListRepositoryImpl(usersService = usersService, handleResponse = handleResponse)
    }

    @Provides
    @Singleton
    fun provideUserDetailsRepository(
        userDetailsService: UserDetailsService,
    ): UserDetailsRepository {
        return UserDetailsRepositoryImpl(userDetailsService)
    }
}