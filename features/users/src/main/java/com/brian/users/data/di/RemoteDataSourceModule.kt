package com.brian.users.data.di

import com.brian.users.data.network.datasource.UsersRemoteDataSource
import com.brian.users.data.network.datasource.UsersRemoteDataSourceImpl
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