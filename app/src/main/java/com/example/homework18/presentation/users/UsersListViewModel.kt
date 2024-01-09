package com.example.homework18.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework18.data.common.Resource
import com.example.homework18.domain.users.usecase.UserListUseCase
import com.example.homework18.presentation.mapper.toPresentationModel
import com.example.homework18.presentation.model.UserPresentationModel
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

class UsersListViewModel @Inject constructor(private val userListUseCase: UserListUseCase) :
    ViewModel() {
    private val _usersFlow = MutableStateFlow<Resource<List<UserPresentationModel>>?>(null)
    val usersFlow: StateFlow<Resource<List<UserPresentationModel>>?> = _usersFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<NavigationEvent>()
    val navigationFlow: SharedFlow<NavigationEvent> = _navigationFlow.asSharedFlow()

    private val _selectedUsers = MutableStateFlow<List<UserPresentationModel>>(emptyList())

    init {
        getUsers()
    }

    fun onEvent(event : UsersListEvent){

    }

    fun onClick(user: UserPresentationModel) {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationEvent.NavigateToDetailsPage(user.id))
        }
    }

    fun deleteSelectedUsers() {
        viewModelScope.launch {
            val selectedUserIds = _selectedUsers.value.map { it.id }
            val currentUsersResource = _usersFlow.value

            if (currentUsersResource is Resource.Success) {
                val remainingUsers =
                    currentUsersResource.data.filterNot { it.id in selectedUserIds }
                _usersFlow.value = Resource.Success(remainingUsers)

                _selectedUsers.value = emptyList()
            }
        }
    }

    fun handleSelectionEvent(event: UserSelectionEvent) {
        when (event) {
            is UserSelectionEvent.ItemSelected -> {
                _selectedUsers.value = _selectedUsers.value + event.userPresentationModel
            }

            is UserSelectionEvent.ItemUnselected -> {
                _selectedUsers.value = _selectedUsers.value - event.userPresentationModel
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            userListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val userPresentationModels = resource.data.map { it.toPresentationModel() }
                        _usersFlow.value = Resource.Success(userPresentationModels)
                    }

                    is Resource.Loading -> _usersFlow.value = Resource.Loading()
                    is Resource.Error -> _usersFlow.value = Resource.Error(resource.errorMessage)
                }
            }
        }
    }
}

sealed class NavigationEvent() {
    data class NavigateToDetailsPage(val userId: Int) : NavigationEvent()
}