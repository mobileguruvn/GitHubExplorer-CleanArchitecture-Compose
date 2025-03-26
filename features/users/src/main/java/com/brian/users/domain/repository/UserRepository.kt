package com.brian.users.domain.repository

import androidx.paging.PagingData
import com.brian.users.domain.model.User
import com.brian.users.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<PagingData<User>>

    fun getUserDetail(loginUserName: String): Flow<Result<UserDetail>>
}