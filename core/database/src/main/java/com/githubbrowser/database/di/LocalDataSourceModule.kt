package com.githubbrowser.database.di

import com.githubbrowser.database.UsersLocalDataSource
import com.githubbrowser.database.UsersLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindUserLocalDataSource(impl: UsersLocalDataSourceImpl): UsersLocalDataSource
}