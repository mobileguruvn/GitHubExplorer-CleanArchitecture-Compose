package com.brian.users.data.network.datasource

import com.brian.users.data.network.api.UserService
import com.brian.users.data.network.model.UserDetailDto
import com.brian.users.data.network.model.UserDto
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

interface UsersRemoteDataSource {
    suspend fun fetchUsers(perPage: Int, since: Int): Result<List<UserDto>>
    suspend fun fetchUserDetail(loginUsername: String): Result<UserDetailDto>
}

class UsersRemoteDataSourceImpl @Inject constructor(private val githubApi: UserService) :
    UsersRemoteDataSource {
    override suspend fun fetchUsers(
        perPage: Int,
        since: Int,
    ): Result<List<UserDto>> {
        return try {
            val response: Response<List<UserDto>> = githubApi.getUsers(perPage, since)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: run {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Timber.tag("UsersRemoteDataSource").e("Error fetching users: ${response.code()}")
                val errorMessage = parseErrorMessage(response.errorBody())
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Timber.tag("UsersRemoteDataSource").e(ex, "Error fetching users")
            Result.failure(ex)
        }
    }

    override suspend fun fetchUserDetail(loginUsername: String): Result<UserDetailDto> {
        return try {
            val response: Response<UserDetailDto> =
                githubApi.getUserDetail(loginUsername = loginUsername)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: run {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Timber.tag("UsersRemoteDataSource")
                    .e("Error fetching user detail: ${response.code()}")
                val errorMessage = parseErrorMessage(response.errorBody())
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Timber.tag("UsersRemoteDataSource").e(ex, "Error fetching user detail")
            Result.failure(ex)
        }
    }

    private fun parseErrorMessage(errorBody: ResponseBody?): String {
        return try {
            val errorStr = errorBody?.string() ?: "Unknown error"
            val json = Gson().fromJson(errorStr, Map::class.java)
            json["message"]?.toString() ?: "Unknown error"
        } catch (ex: Exception) {
            "An error occurred while parsing the error message"
        }
    }
}