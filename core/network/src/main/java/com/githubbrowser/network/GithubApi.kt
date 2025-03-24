package com.githubbrowser.network

import com.githubbrowser.network.model.UserDetailDto
import com.githubbrowser.network.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int,
    ): Response<List<UserDto>>

    @GET("users/{login_username}")
    suspend fun getUserDetail(@Path("login_username") loginUsername: String): Response<UserDetailDto>
}