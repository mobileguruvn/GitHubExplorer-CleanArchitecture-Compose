package com.brian.users.data.di

import com.brian.users.data.network.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserServiceModule {

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}