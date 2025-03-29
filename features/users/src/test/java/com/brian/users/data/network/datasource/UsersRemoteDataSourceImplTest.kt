package com.brian.users.data.network.datasource

import com.brian.users.data.network.api.UserService
import com.brian.users.data.network.model.UserDetailDto
import com.brian.users.data.network.model.UserDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import timber.log.Timber

class UsersRemoteDataSourceImplTest {

    private lateinit var userService: UserService
    private lateinit var dataSource: UsersRemoteDataSource

    @Before
    fun setup() {
        Timber.plant(object : Timber.Tree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                println("[$priority][$tag] $message")
            }
        })
        userService = mockk()
        dataSource = UsersRemoteDataSourceImpl(userService)
    }

    @Test
    fun `test fetchUsers return success result`() = runTest {
        // Given
        val usersResponse =
            listOf(UserDto(id = 1, login = "user1", avatarUrl = "avatarUrl1", htmlUrl = "htmlUrl1"))
        coEvery { userService.getUsers(perPage = 10, since = 0) } returns Response.success(
            usersResponse
        )

        // When
        val result = dataSource.fetchUsers(perPage = 10, since = 0)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(usersResponse, result.getOrNull())

    }

    @Test
    fun `test fetchUsers return failure result with body null`() = runTest {
        // Given
        coEvery { userService.getUsers(any(), any()) } returns Response.success(null)

        // When
        val result = dataSource.fetchUsers(perPage = 10, since = 0)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Response body is null", result.exceptionOrNull()?.message)

    }

    @Test
    fun `test fetchUsers return failure with parsed error message`() = runTest {
        // Given
        val errorJson = """{"message": "Invalid request"}"""
        val errorBody = errorJson.toResponseBody("application/json".toMediaTypeOrNull())

        val errorResponse: Response<List<UserDto>> = Response.error(404, errorBody)
        coEvery { userService.getUsers(perPage = 10, since = 0) } returns errorResponse

        // When
        val result = dataSource.fetchUsers(perPage = 10, since = 0)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Invalid request", result.exceptionOrNull()?.message)
    }

    @Test
    fun `test fetchUsers return failure with exception`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { userService.getUsers(any(), any()) } throws exception

        // When
        val result = dataSource.fetchUsers(perPage = 10, since = 0)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `test fetchUserDetail return success result`() = runTest {
        // Given
        val userDetailResponse = UserDetailDto(
            id = 1,
            location = "New York",
            followers = 100,
            following = 50
        )
        coEvery { userService.getUserDetail("user1") } returns Response.success(
            userDetailResponse
        )

        // When
        val result = dataSource.fetchUserDetail(loginUsername = "user1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(userDetailResponse, result.getOrNull())

    }

    @Test
    fun `test fetchUserDetail return failure result with body null`() = runTest {
        // Given
        coEvery { userService.getUserDetail("user1") } returns Response.success(null)

        // When
        val result = dataSource.fetchUserDetail(loginUsername = "user1")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Response body is null", result.exceptionOrNull()?.message)

    }

    @Test
    fun `test fetchUserDetail return failure with parsed error message`() = runTest {
        // Given
        val errorJson = """{"message": "Invalid request"}"""
        val errorBody = errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        val errorResponse: Response<UserDetailDto> = Response.error(404, errorBody)
        coEvery { userService.getUserDetail("user1") } returns errorResponse

        // When
        val result = dataSource.fetchUserDetail(loginUsername = "user1")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Invalid request", result.exceptionOrNull()?.message)
    }

    @Test
    fun `test fetchUserDetail return failure with exception`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { userService.getUserDetail("user1") } throws exception

        // When
        val result = dataSource.fetchUserDetail(loginUsername = "user1")

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }
}