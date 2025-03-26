package com.githubbrowser.data.di

import com.githubbrowser.data.mapper.UserMapper
import com.githubbrowser.data.mapper.UserMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Singleton
    @Binds
    abstract fun bindUserMapper(impl: UserMapperImpl): UserMapper
}