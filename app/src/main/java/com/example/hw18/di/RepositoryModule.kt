package com.example.hw18.di

import com.example.hw18.data.repository.UserRepositoryImpl
import com.example.hw18.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}