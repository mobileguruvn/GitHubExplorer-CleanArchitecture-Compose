package com.brian.users.presentation.mapper

import com.brian.users.presentation.userdetail.UserDetailItemUi
import com.brian.users.presentation.users.UserUiState
import com.brian.users.domain.model.User
import com.brian.users.domain.model.UserDetail
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