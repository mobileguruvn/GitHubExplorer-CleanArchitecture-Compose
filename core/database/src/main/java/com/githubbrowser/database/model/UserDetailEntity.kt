package com.githubbrowser.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_detail",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserDetailEntity(
    @PrimaryKey val id: Int,
    val location: String? = null,
    val followers: Int,
    val following: Int
)
