package com.githubbrowser.network

import android.util.Log
import com.githubbrowser.network.model.UserDetailDto
import com.githubbrowser.network.model.UserDto
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface UsersRemoteDataSource {
    suspend fun fetchUsers(perPage: Int, since: Int): Result<List<UserDto>>
    suspend fun fetchUserDetail(loginUsername: String): Result<UserDetailDto>
}

class UsersRemoteDataSourceImpl @Inject constructor(private val githubApi: GithubApi) :
    UsersRemoteDataSource {
    override suspend fun fetchUsers(
        perPage: Int,
        since: Int,
    ): Result<List<UserDto>> {
        return try {
            val response: Response<List<UserDto>> = githubApi.getUsers(perPage, since)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Log.e("UsersRemoteDataSource", "Error fetching users: ${response.code()}")
                val errorMessage = parseErrorMessage(response.errorBody()?.string())
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Log.e("UsersRemoteDataSource", "Error fetching users", ex)
            Result.failure(ex)
        }
    }

    override suspend fun fetchUserDetail(loginUsername: String): Result<UserDetailDto> {
        return try {
            val response: Response<UserDetailDto> =
                githubApi.getUserDetail(loginUsername = loginUsername)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Log.e(
                    "UsersRemoteDataSource",
                    "Error fetching user detail: ${response.code()}"
                )
                val errorMessage = parseErrorMessage(response.errorBody()?.string())
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        Log.e("UsersRemoteDataSource", "Error body: $errorBody")
        if (errorBody.isNullOrEmpty()) {
            return "An error occurred while parsing the error message"
        }
        return try {
            val json = JSONObject(errorBody.trimIndent())
            json.getString("message")
        } catch (e: JSONException) {
            e.printStackTrace()
            "An error occurred while parsing the error message"
        }
    }
}