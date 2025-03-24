package com.githubbrowser.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithDetailEntity(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val userDetail: UserDetailEntity?,
)
