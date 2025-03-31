package com.brian.users.domain.usecase

import androidx.paging.PagingData
import com.brian.users.domain.model.User
import com.brian.users.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun invoke_whenCalled_returnsPagingDataFromRepository() = runTest {
        //Given
        val expectedUser =
            User(1, "brian", "https://example.com/avatar.jpg", htmlUrl = "https://github.com/brian")

        val pagingData = PagingData.from(listOf(expectedUser))
        coEvery { userRepository.getUsers() } returns flowOf(pagingData)

        // When
        val result = getUsersUseCase.invoke().first()

        // Then
        assertEquals(pagingData, result)

    }
}