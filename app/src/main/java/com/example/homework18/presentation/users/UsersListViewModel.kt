package com.example.homework18.presentation.users

import android.util.Log.d
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

    private val _usersFlow = MutableStateFlow<List<UserPresentationModel>>(emptyList())
    val usersFlow: StateFlow<List<UserPresentationModel>> = _usersFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<NavigationEvent>()
    val navigationFlow: SharedFlow<NavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        getUsers()
    }

    fun onEvent(event : UsersListEvent){
        when(event){
            is UsersListEvent.ItemClicked -> handleUserClicked(user = event.userPresentationModel)
            is UsersListEvent.ItemSelected -> handleUserSelected(user = event.userPresentationModel)
            is UsersListEvent.ItemUnselected -> handleUserUnselected(user = event.userPresentationModel)
            is UsersListEvent.DeleteSelectedUsers -> deleteSelectedUsers()
            is UsersListEvent.RefreshPage -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            userListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val userPresentationModels = resource.data.map { it.toPresentationModel() }
                        _usersFlow.value = userPresentationModels.map { it.copy(status = UserPresentationModel.Status.Success) }
                        _isLoading.value = false
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Error -> {
                        _usersFlow.value = _usersFlow.value.map { it.copy(status = UserPresentationModel.Status.Error) }
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun handleUserClicked(user: UserPresentationModel) {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationEvent.NavigateToDetailsPage(user.id))
        }
    }

    private fun handleUserSelected(user: UserPresentationModel) {
        _usersFlow.value = _usersFlow.value.map {
            if (it.id == user.id) it.copy(isSelected = UserPresentationModel.IsItemSelected.SELECTED)
            else it
        }
    }

    private fun handleUserUnselected(user: UserPresentationModel) {
        _usersFlow.value = _usersFlow.value.map {
            if (it.id == user.id) it.copy(isSelected = UserPresentationModel.IsItemSelected.NOT_SELECTED)
            else it
        }
    }

    private fun deleteSelectedUsers() {
        _usersFlow.value = _usersFlow.value.filter {
            it.isSelected != UserPresentationModel.IsItemSelected.SELECTED
        }
    }
}

sealed class NavigationEvent() {
    data class NavigateToDetailsPage(val userId: Int) : NavigationEvent()
}