package com.brian.users.mapper

import com.brian.users.ui.userdetail.UserDetailItemUi
import com.brian.users.ui.users.UserUiState
import com.githubbrowser.domain.model.User
import com.githubbrowser.domain.model.UserDetail
import javax.inject.Inject

interface UserUiMapper {
    fun toUiState(user: User): UserUiState

    fun toUserDetailItemUi(userDetail: UserDetail): UserDetailItemUi
}

class UserUiMapperImpl @Inject constructor() : UserUiMapper {
    override fun toUiState(user: User): UserUiState {
        return UserUiState(
            login = user.login,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
    }

    override fun toUserDetailItemUi(userDetail: UserDetail): UserDetailItemUi {
        return UserDetailItemUi(
            login = userDetail.login,
            avatarUrl = userDetail.avatarUrl,
            htmlUrl = userDetail.htmlUrl,
            location = userDetail.location,
            followers = userDetail.followers,
            following = userDetail.following
        )
    }
}