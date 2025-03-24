package com.githubbrowser.network.di

import com.githubbrowser.network.UsersRemoteDataSource
import com.githubbrowser.network.UsersRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindRemoteDataSource(impl: UsersRemoteDataSourceImpl): UsersRemoteDataSource

}