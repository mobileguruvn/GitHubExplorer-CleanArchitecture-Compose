package com.brian.users.domain.usecase

import com.brian.users.domain.model.UserDetail
import com.brian.users.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserDetailUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUserDetailUseCase = GetUserDetailUseCase(userRepository)
    }

    @Test
    fun `invoke should return user detail for given login`() = runTest {
        // Given
        val login = "brian"
        val expectedUserDetail = UserDetail(
            login = login,
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl",
            location = "location",
            followers = 10,
            following = 100
        )

        val expectedResult = Result.success(expectedUserDetail)

        coEvery { userRepository.getUserDetail(loginUserName = login) } returns flowOf(
            expectedResult
        )

        // When
        val result = getUserDetailUseCase.invoke(login).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedUserDetail, result.getOrNull())
        coVerify { userRepository.getUserDetail(loginUserName = login) }
    }
}