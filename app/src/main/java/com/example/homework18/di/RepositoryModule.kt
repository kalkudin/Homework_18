package com.example.homework18.di

import com.example.homework18.data.users.repository.UsersListRepositoryImpl
import com.example.homework18.data.service.UsersService
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
    fun provideUsersRepository(usersService: UsersService) : UsersListRepository{
        return UsersListRepositoryImpl(usersService = usersService)
    }
}