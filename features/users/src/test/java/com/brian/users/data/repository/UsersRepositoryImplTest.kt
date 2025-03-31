package com.brian.users.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.brian.users.data.mapper.UserMapper
import com.brian.users.data.network.datasource.UsersRemoteDataSource
import com.brian.users.domain.model.User
import com.githubbrowser.database.UsersLocalDataSource
import com.githubbrowser.database.model.UserEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}