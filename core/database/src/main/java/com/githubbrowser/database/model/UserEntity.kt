package com.githubbrowser.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Long = 0,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)