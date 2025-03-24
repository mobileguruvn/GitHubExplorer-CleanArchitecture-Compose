package com.githubbrowser.domain.model

data class UserDetail(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String? = null,
    val followers: Int,
    val following: Int,
)
