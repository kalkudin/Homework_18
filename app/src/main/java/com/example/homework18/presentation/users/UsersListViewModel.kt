package com.example.homework18.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import com.example.homework18.domain.users.repository.UsersListRepository
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
class UsersListViewModel @Inject constructor (private val usersListRepository: UsersListRepository): ViewModel() {
    private val _usersFlow = MutableStateFlow<Resource<List<User>>?>(null)
    val usersFlow : StateFlow<Resource<List<User>>?> = _usersFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<NavigationEvent>()
    val navigationFlow : SharedFlow<NavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        getUsers()
    }

    fun onClick(user: User) {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationEvent.NavigateToDetailsPage(user.id))
        }
    }

    private fun getUsers(){
        viewModelScope.launch {
            usersListRepository.getUsersList().collect{ userList ->
                _usersFlow.value = userList
            }
        }
    }
}

sealed class NavigationEvent(){
    data class NavigateToDetailsPage(val userId : Int) : NavigationEvent()
}