package com.brian.users.data.mapper

import com.brian.users.data.network.model.UserDetailDto
import com.brian.users.data.network.model.UserDto
import com.githubbrowser.database.model.UserDetailEntity
import com.githubbrowser.database.model.UserEntity
import com.githubbrowser.database.model.UserWithDetailEntity
import junit.framework.TestCase.assertEquals
import org.junit.Test


class UsersRepositoryImplTest {

    private val userMapper: UserMapper = UserMapperImpl()

    @Test
    fun `test UserEntity mapping toUser correctly`() {
        // Given
        val userEntity = UserEntity(
            id = 1,
            login = "testUser",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl"
        )

        // When
        val user = userMapper.toUser(userEntity)

        // Then
        assertEquals(1, user.id)
        assertEquals("testUser", user.login)
        assertEquals("avatarUrl", user.avatarUrl)
        assertEquals("htmlUrl", user.htmlUrl)
    }

    @Test
    fun `test UserDto mapping toUserEntity correctly`() {
        // Given
        val userDto = UserDto(
            id = 1,
            login = "testUser",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl"
        )

        // When
        val userEntity = userMapper.toUserEntity(userDto)

        // Then
        assertEquals(1, userEntity.id)
        assertEquals("testUser", userEntity.login)
        assertEquals("avatarUrl", userEntity.avatarUrl)
        assertEquals("htmlUrl", userEntity.htmlUrl)

    }

    @Test
    fun `test UserWithDetailEntity mapping toUserDetail correctly`() {
        // Given
        val userWithDetailEntity = UserWithDetailEntity(
            user = UserEntity(
                id = 1,
                login = "testUser",
                avatarUrl = "avatarUrl",
                htmlUrl = "htmlUrl"
            ),
            userDetail = UserDetailEntity(
                id = 1,
                location = "location",
                followers = 10,
                following = 20
            )
        )

        // When
        val userDetail = userMapper.toUserDetail(userWithDetailEntity)

        // Then
        assertEquals("testUser", userDetail.login)
        assertEquals("avatarUrl", userDetail.avatarUrl)
        assertEquals("htmlUrl", userDetail.htmlUrl)
        assertEquals("location", userDetail.location)
        assertEquals(10, userDetail.followers)
        assertEquals(20, userDetail.following)

    }

    @Test
    fun `test UserDetailDto mapping toUserDetailEntity correctly`() {
        // Given
        val userDetailDto = UserDetailDto(
            id = 1,
            location = "location",
            followers = 10,
            following = 20
        )

        // When
        val userDetailEntity = userMapper.toUserDetailEntity(userDetailDto)

        // Then
        assertEquals(1, userDetailEntity.id)
        assertEquals("location", userDetailEntity.location)
        assertEquals(10, userDetailEntity.followers)
        assertEquals(20, userDetailEntity.following)
    }

}