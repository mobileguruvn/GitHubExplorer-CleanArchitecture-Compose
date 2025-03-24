package com.githubbrowser.data.di

import com.githubbrowser.data.repository.UsersRepositoryImpl
import com.githubbrowser.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGitHubRepository(repository: UsersRepositoryImpl): UserRepository
}