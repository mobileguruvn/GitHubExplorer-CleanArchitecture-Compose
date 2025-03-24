package com.githubbrowser.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.githubbrowser.data.mapper.UserMapper
import com.githubbrowser.database.GitHubUserDatabase
import com.githubbrowser.database.model.RemoteKeys
import com.githubbrowser.database.model.UserEntity
import com.githubbrowser.network.UsersRemoteDataSource
import java.io.IOException
import javax.inject.Inject


private const val GITHUB_STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val gitHubUserDatabase: GitHubUserDatabase,
    private val userMapper: UserMapper
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>,
    ): MediatorResult {
        Log.d("RemoteMediator", "LoadType: $loadType")
        val since = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("RemoteMediator", "LoadType.REFRESH triggered")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                Log.d("RemoteMediator", "LoadType.PREPEND triggered")
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                Log.d("RemoteMediator", "LoadType.APPEND triggered")
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        Log.d("RemoteMediator", "Fetching data for since = $since")

        try {
            val response =
                remoteDataSource.fetchUsers(
                    perPage = state.config.pageSize,
                    since = since.toInt()
                )
            if (!response.isSuccess) {
                return MediatorResult.Error(
                    response.exceptionOrNull() ?: Exception("Unknown error")
                )
            }

            val users = response.getOrNull() ?: emptyList()
            val endOfPaginationReached = users.isEmpty()

            Log.d(
                "RemoteMediator",
                "API returned ${users.size} users. End of pagination: $endOfPaginationReached"
            )

            gitHubUserDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    gitHubUserDatabase.remoteKeysDao().clearRemoteKeys()
                    gitHubUserDatabase.userDao().clearUsers()
                }
                val prevKey =
                    if (since == GITHUB_STARTING_PAGE_INDEX) null else since - 1
                val nextKey = if (endOfPaginationReached) null else users.last().id
                val keys = users.map { user ->
                    RemoteKeys(userId = user.id, prevKey = prevKey, nextKey = nextKey?.toInt())
                }
                Log.d(
                    "RemoteMediator",
                    "PrevKey: $prevKey, NextKey: $nextKey"
                )
                gitHubUserDatabase.remoteKeysDao().insertAll(keys)
                gitHubUserDatabase.userDao().insertUsers(users.map { userMapper.toUserEntity(it) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e("RemoteMediator", "Error fetching data", e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                // Get the remote keys of the last item retrieved
                gitHubUserDatabase.remoteKeysDao().remoteKeysUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                // Get the remote keys of the first items retrieved
                gitHubUserDatabase.remoteKeysDao().remoteKeysUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>,
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                gitHubUserDatabase.remoteKeysDao().remoteKeysUserId(userId)
            }
        }
    }
}