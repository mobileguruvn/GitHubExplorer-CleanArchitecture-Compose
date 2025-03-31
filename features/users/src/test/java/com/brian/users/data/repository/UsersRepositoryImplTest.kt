package com.brian.users.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.brian.users.data.mapper.UserMapper
import com.brian.users.data.network.datasource.UsersRemoteDataSource
import com.brian.users.data.network.model.UserDetailDto
import com.brian.users.domain.model.User
import com.brian.users.domain.model.UserDetail
import com.githubbrowser.database.UsersLocalDataSource
import com.githubbrowser.database.model.UserDetailEntity
import com.githubbrowser.database.model.UserEntity
import com.githubbrowser.database.model.UserWithDetailEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class UsersRepositoryImplTest {

    private val remoteDataSource = mockk<UsersRemoteDataSource>()
    private val localDataSource = mockk<UsersLocalDataSource>()
    private val userMapper = mockk<UserMapper>()
    private val remoteMediator = mockk<UsersRemoteMediator>()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var userRepository: UsersRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = UsersRepositoryImpl(
            remoteDataSource,
            localDataSource,
            remoteMediator,
            userMapper,
            testDispatcher
        )
    }

    @Test
    fun `test getUsers() returns mapped PagingData from FakePagingSource`() = runTest {
        // Given
        val userEntities = listOf(
            UserEntity(1, "testUser", "avatarUrl", "htmlUrl"),
            UserEntity(2, "testUser2", "avatarUrl2", "htmlUrl2")
        )
        val mappedUsers = listOf<User>(
            User(1, "testUser", "avatarUrl", "htmlUrl"),
            User(2, "testUser2", "avatarUrl2", "htmlUrl2")
        )
        val fakePagingSource = FakePagingSource(userEntities)

        every { userMapper.toUser(userEntities[0]) } returns mappedUsers[0]
        every { userMapper.toUser(userEntities[1]) } returns mappedUsers[1]
        every { localDataSource.getUsers() } returns FakePagingSource(userEntities)

        // When
        val pagingFlow = userRepository.getUsers()
        val pagingData = PagingData.from(userEntities)
        val mappedPagingData = pagingData.map { userMapper.toUser(it) }

        // Then


    }


    @Test
    fun `getUserDetail should return failed when response is Failure`() = runTest {
        // Given
        val login = "brian"
        val exception = Exception("Network error")

        coEvery { remoteDataSource.fetchUserDetail(login) } returns Result.failure(exception)

        // When
        val result = userRepository.getUserDetail(login).first()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getUserDetail given null response should return failed`() = runTest {
        // Given
        val login = "brian"
        coEvery { remoteDataSource.fetchUserDetail(login) } returns Result.failure(
            NullPointerException("User detail is null")
        )

        // When
        val result = userRepository.getUserDetail(login).first()

        // Then
        assertTrue(result.isFailure)
        assertEquals("User detail is null", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getUserDetail should return success when response is successful`() = runTest {
        // Given
        val login = "brian"
        val mockUserId = 123
        val mockUserDetail = mockk<UserDetailDto> {
            every { id } returns mockUserId
        }
        val mockUserDetailEntity = mockk<UserDetailEntity>()
        val flowOfResult = mockk<UserWithDetailEntity>()
        val expectedMapper = mockk<UserDetail>()

        coEvery { remoteDataSource.fetchUserDetail(login) } returns Result.success(mockUserDetail)
        coEvery { userMapper.toUserDetailEntity(mockUserDetail) } returns mockUserDetailEntity
        coEvery { localDataSource.insertUserDetails(mockUserDetailEntity) } just Runs
        coEvery { localDataSource.getUserWithDetail(mockUserId) } returns flowOf(
            flowOfResult
        )
        every { userMapper.toUserDetail(any()) } returns expectedMapper

        // When
        val result = userRepository.getUserDetail(login).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedMapper, result.getOrNull())
    }

    @Test
    fun `getUserDetail local insert throws exception emit failure result`() = runTest {
        // Given
        val login = "brian"
        val mockUserDetail = mockk<UserDetailDto>()
        val mockUserDetailEntity = mockk<UserDetailEntity>()
        val exception = Exception("Database error")
        coEvery { remoteDataSource.fetchUserDetail(login) } returns Result.success(mockUserDetail)
        coEvery { userMapper.toUserDetailEntity(mockUserDetail) } returns mockUserDetailEntity
        coEvery { localDataSource.insertUserDetails(mockUserDetailEntity) } throws exception

        // When
        val result = userRepository.getUserDetail(login).first()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}