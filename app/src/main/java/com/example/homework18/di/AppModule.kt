package com.example.homework18.di

import com.example.homework18.data.service.UserDetailsService
import com.example.homework18.data.service.UsersService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL_MOCKY = "https://run.mocky.io"
    private const val BASE_URL_REQRES = "https://reqres.in"

    @Provides
    @Singleton
    @Named("MockyRetrofit")
    fun provideMockyRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MOCKY)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    @Named("ReqresRetrofit")
    fun provideReqresRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_REQRES)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersService(@Named("MockyRetrofit") retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDetailsService(@Named("ReqresRetrofit") retrofit: Retrofit): UserDetailsService {
        return retrofit.create(UserDetailsService::class.java)
    }
}