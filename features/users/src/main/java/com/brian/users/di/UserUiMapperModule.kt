package com.brian.users.di

import com.brian.users.mapper.UserUiMapper
import com.brian.users.mapper.UserUiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserUiMapperModule {

    @Binds
    abstract fun bindUserUiMapper(impl: UserUiMapperImpl): UserUiMapper
}