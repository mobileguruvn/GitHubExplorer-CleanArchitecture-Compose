package com.githubbrowser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.githubbroswer.common.Dispatcher
import com.githubbroswer.common.GitHubDispatcher
import com.githubbrowser.data.mapper.UserMapper
import com.githubbrowser.database.UsersLocalDataSource
import com.githubbrowser.domain.model.User
import com.githubbrowser.domain.model.UserDetail
import com.githubbrowser.domain.repository.UserRepository
import com.githubbrowser.network.UsersRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class UsersRepositoryImpl @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val usersLocalDataSource: UsersLocalDataSource,
    private val usersRemoteMediator: UsersRemoteMediator,
    private val userMapper: UserMapper,
    @Dispatcher(GitHubDispatcher.IO)
    private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
            ),
            remoteMediator = usersRemoteMediator,
            pagingSourceFactory = {
                usersLocalDataSource.getUsers()
            }
        ).flow.map { pagingData ->
            pagingData.map {
                userMapper.toUser(it)
            }
        }.flowOn(ioDispatcher)
    }

    override fun getUserDetail(loginUserName: String): Flow<Result<UserDetail>> {
        return flow {
            try {
                val response = usersRemoteDataSource.fetchUserDetail(loginUserName)
                if (response.isSuccess) {
                    val userDetail = response.getOrNull()
                    if (userDetail != null) {
                        usersLocalDataSource.insertUserDetails(
                            userMapper.toUserDetailEntity(
                                userDetail
                            )
                        )
                        emitAll(
                            usersLocalDataSource.getUserWithDetail(id = userDetail.id.toString())
                                .map {
                                    Result.success(userMapper.toUserDetail(it))
                                })
                    } else {
                        emit(Result.failure(Exception("User detail is null")))
                    }
                } else {
                    emit(Result.failure(response.exceptionOrNull() ?: Exception("Unknown error")))
                }
            } catch (ex: Exception) {
                emit(Result.failure(ex))
            }
        }.flowOn(ioDispatcher)
    }
}