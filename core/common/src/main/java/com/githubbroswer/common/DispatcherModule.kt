package com.githubbroswer.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Dispatcher(GitHubDispatcher.IO)
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(GitHubDispatcher.Default)
    fun providesDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}