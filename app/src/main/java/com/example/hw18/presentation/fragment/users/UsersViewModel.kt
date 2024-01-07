package com.example.hw18.presentation.fragment.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw18.domain.useCase.GetUsersUseCase
import com.example.hw18.presentation.model.UserUiModel
import com.example.hw18.presentation.toUiMapper.UserUiMapper.mapToUiModel
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
            when (val userResource = getUsersUseCase()) {
                is Resource.Success -> {
                    _state.value =
                        _state.value.copy(
                            users = userResource.data?.map { it.mapToUiModel() } ?: emptyList(),
                            isLoading = false)
                }
                is Resource.Error -> {
                    _error.emit(userResource.message ?: "")
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    fun selectionMode(start: Boolean) {
        if (start) {
            _state.value = _state.value.copy(isSelectionModeOn = true)
        } else {
            _state.value =
                _state.value.copy(
                    selectedUserIds = mutableSetOf(),
                    isSelectionModeOn = false
                )
        }
    }

    fun selectItem(id: Int, select: Boolean) {
        if (select) {
            getSelectedIds().add(id)
        } else if (getSelectedIds().contains(id)) {
            getSelectedIds().remove(id)
            if (getSelectedIds().isEmpty()) {
                _state.value = _state.value.copy(
                    isSelectionModeOn = false)
            }
        }
    }

    private fun getSelectedIds(): MutableSet<Int> {
        return _state.value.selectedUserIds
    }

    fun deleteSelectedItems() {
        val filteredList = _state.value.users.filter { user ->
            !_state.value.selectedUserIds.contains(user.id)
        }
        _state.value = _state.value.copy(users = filteredList)
        selectionMode(start = false)
    }

    data class ViewState(
        val users: List<UserUiModel> = emptyList(),
        val selectedUserIds: MutableSet<Int> = mutableSetOf(),
        val isLoading: Boolean = false,
        val isSelectionModeOn: Boolean = false
    )
}