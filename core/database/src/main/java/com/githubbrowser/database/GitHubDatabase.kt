package com.githubbrowser.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubbrowser.database.dao.RemoteKeysDao
import com.githubbrowser.database.dao.UserDao
import com.githubbrowser.database.model.RemoteKeys
import com.githubbrowser.database.model.UserDetailEntity
import com.githubbrowser.database.model.UserEntity

@Database(
    entities = [UserEntity::class, UserDetailEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class GitHubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}