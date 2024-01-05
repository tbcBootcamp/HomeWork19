package com.example.hw18.presentation.fragment.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw18.domain.model.User
import com.example.hw18.domain.useCase.GetUsersUseCase
import com.example.hw18.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> get() = _error.asSharedFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val user = getUsersUseCase()) {
                is Resource.Success -> {
                    _state.value =
                        _state.value.copy(users = user.data ?: emptyList(), isLoading = false)
                }
                is Resource.Error -> {
                    _error.emit(user.message ?: "")
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    data class ViewState(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false
    )
}