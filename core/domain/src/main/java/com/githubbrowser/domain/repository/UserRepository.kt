package com.githubbrowser.domain.repository

import androidx.paging.PagingData
import com.githubbrowser.domain.model.User
import com.githubbrowser.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers() : Flow<PagingData<User>>

    fun getUserDetail(loginUserName: String) : Flow<Result<UserDetail>>
}