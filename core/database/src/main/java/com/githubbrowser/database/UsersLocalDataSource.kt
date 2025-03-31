package com.githubbrowser.database

import androidx.paging.PagingSource
import com.githubbrowser.database.dao.UserDao
import com.githubbrowser.database.model.UserDetailEntity
import com.githubbrowser.database.model.UserEntity
import com.githubbrowser.database.model.UserWithDetailEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UsersLocalDataSource {
    fun getUsers() : PagingSource<Int, UserEntity>
    suspend fun insertUsers(users: List<UserEntity>)
    suspend fun insertUserDetails(userDetail: UserDetailEntity)
    fun getUserWithDetail(id: Int) : Flow<UserWithDetailEntity>
    suspend fun clearUsers()
}

class UsersLocalDataSourceImpl @Inject constructor(private val userDao: UserDao) : UsersLocalDataSource {
    override fun getUsers(): PagingSource<Int, UserEntity> {
        return userDao.getUsers()
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertUsers(users)
    }

    override suspend fun insertUserDetails(userDetail: UserDetailEntity) {
        userDao.insertUserDetail(userDetail)
    }

    override fun getUserWithDetail(id: Int): Flow<UserWithDetailEntity> {
        return userDao.getUserWithDetail(id)
    }

    override suspend fun clearUsers() {
        userDao.clearUsers()
    }
}