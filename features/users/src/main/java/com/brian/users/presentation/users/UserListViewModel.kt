package com.brian.users.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.brian.users.presentation.mapper.UserUiMapper
import com.brian.users.domain.repository.UserRepository
import com.brian.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val userUiMapper: UserUiMapper,
) : ViewModel() {

    val usersPaging: StateFlow<PagingData<UserUiState>> = getUsersUseCase()
        .map { pagingData -> pagingData.map { userUiMapper.toUiState(it) } }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope, started = SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )
}

data class UserUiState(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
)