package com.brian.users.data.di

import com.brian.users.data.mapper.UserMapper
import com.brian.users.data.mapper.UserMapperImpl
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