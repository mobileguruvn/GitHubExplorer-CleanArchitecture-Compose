package com.githubbrowser.network.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
)
