package com.brian.users.domain.usecase

import com.brian.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: String) = userRepository.getUserDetail(userId)
}