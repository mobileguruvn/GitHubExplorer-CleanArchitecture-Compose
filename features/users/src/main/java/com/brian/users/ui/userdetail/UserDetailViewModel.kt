package com.brian.users.ui.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brian.users.mapper.UserUiMapper
import com.brian.users.navigation.NAV_ARG_LOGIN_NAME
import com.brian.users.utils.safe
import com.githubbrowser.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
    private val userUiMapper: UserUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    private val loginName
        get() = savedStateHandle.get<String>(NAV_ARG_LOGIN_NAME).safe()

    init {
        getUserDetail()
    }

    fun getUserDetail() {
        _uiState.value = UserDetailUiState.Loading
        viewModelScope.launch {
            userRepository.getUserDetail(loginName)
                .onStart {
                    _uiState.value = UserDetailUiState.Loading
                }
                .collect { result ->
                    result.onSuccess { userDetail ->
                        _uiState.value =
                            UserDetailUiState.Success(userUiMapper.toUserDetailItemUi(userDetail))
                    }
                    result.onFailure {
                        _uiState.value = UserDetailUiState.Error
                    }
                }
        }
    }
}

sealed interface UserDetailUiState {
    data class Success(val userWithDetail: UserDetailItemUi) : UserDetailUiState
    object Error : UserDetailUiState
    object Loading : UserDetailUiState

}

data class UserDetailItemUi(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String? = null,
    val followers: Int,
    val following: Int,
)
