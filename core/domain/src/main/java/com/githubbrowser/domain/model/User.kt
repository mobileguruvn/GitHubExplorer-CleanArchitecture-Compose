package com.githubbrowser.domain.model

data class User(
    val id: Long = 0,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
)
