package com.example.hw18.presentation.fragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw18.domain.model.User
import com.example.hw18.domain.useCase.GetUserDetailsUseCase
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
class UserDetailsViewModel @Inject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> get() = _error.asSharedFlow()

    fun getUserDetails(userId: Int) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val details = getUserDetailsUseCase(userId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(user = details.data, isLoading = false)
                }

                is Resource.Error -> {
                    _error.emit(details.message ?: "")
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    data class ViewState(
        val user: User? = null,
        val isLoading: Boolean = false
    )
}