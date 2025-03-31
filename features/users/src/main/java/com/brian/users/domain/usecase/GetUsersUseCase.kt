package com.brian.users.domain.usecase

import com.brian.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UserRepository
) {
    operator fun invoke() = usersRepository.getUsers()
}