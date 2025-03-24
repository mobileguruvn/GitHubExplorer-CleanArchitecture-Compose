package com.githubbrowser.network.model

import com.google.gson.annotations.SerializedName

data class UserDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)
