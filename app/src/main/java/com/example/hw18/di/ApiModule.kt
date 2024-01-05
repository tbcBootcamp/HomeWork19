package com.example.hw18.di

import com.example.hw18.utils.Constants
import com.example.hw18.data.network.UserDetailsService
import com.example.hw18.data.network.UsersService
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
object ApiModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named("users")
    fun provideUsersRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.USERS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    @Named("details")
    fun provideDetailsRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.USER_DETAILS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideUsersService(@Named("users") retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDetailsService(@Named("details") retrofit: Retrofit): UserDetailsService {
        return retrofit.create(UserDetailsService::class.java)
    }

}