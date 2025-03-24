package com.githubbrowser.database.di

import android.content.Context
import androidx.room.Room
import com.githubbrowser.database.GitHubUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GitHubUserDatabase {
        return Room.databaseBuilder(
            context,
            GitHubUserDatabase::class.java,
            "github_user_database"
        ).build()
    }

    @Provides
    fun provideGitHubUserDao(database: GitHubUserDatabase) = database.userDao()

    @Provides
    fun providesRemoteKeysDao(database: GitHubUserDatabase) = database.remoteKeysDao()

}