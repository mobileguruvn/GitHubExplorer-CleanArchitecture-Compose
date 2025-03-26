package com.githubbrowser.data.mapper

import com.githubbrowser.database.model.UserDetailEntity
import com.githubbrowser.database.model.UserEntity
import com.githubbrowser.database.model.UserWithDetailEntity
import com.githubbrowser.domain.model.User
import com.githubbrowser.domain.model.UserDetail
import com.githubbrowser.network.model.UserDetailDto
import com.githubbrowser.network.model.UserDto
import javax.inject.Inject

interface UserMapper {
    fun toUser(userEntity: UserEntity): User

    fun toUserEntity(userDto: UserDto): UserEntity

    fun toUserDetail(userWithDetailEntity: UserWithDetailEntity): UserDetail

    fun toUserDetailEntity(userDetailDto: UserDetailDto): UserDetailEntity
}

class UserMapperImpl @Inject constructor() : UserMapper {
    override fun toUser(userEntity: UserEntity): User {
        return User(
            id = userEntity.id,
            login = userEntity.login,
            avatarUrl = userEntity.avatarUrl,
            htmlUrl = userEntity.htmlUrl
        )
    }

    override fun toUserEntity(userDto: UserDto): UserEntity {
        return UserEntity(
            id = userDto.id,
            login = userDto.login,
            avatarUrl = userDto.avatarUrl,
            htmlUrl = userDto.htmlUrl
        )
    }

    override fun toUserDetail(userWithDetailEntity: UserWithDetailEntity): UserDetail {
        return UserDetail(
            login = userWithDetailEntity.user.login,
            avatarUrl = userWithDetailEntity.user.avatarUrl,
            htmlUrl = userWithDetailEntity.user.htmlUrl,
            location = userWithDetailEntity.userDetail?.location,
            followers = userWithDetailEntity.userDetail?.followers ?: 0,
            following = userWithDetailEntity.userDetail?.following ?: 0
        )
    }

    override fun toUserDetailEntity(userDetailDto: UserDetailDto): UserDetailEntity {
        return UserDetailEntity(
            id = userDetailDto.id,
            location = userDetailDto.location,
            followers = userDetailDto.followers,
            following = userDetailDto.following,
        )
    }
}