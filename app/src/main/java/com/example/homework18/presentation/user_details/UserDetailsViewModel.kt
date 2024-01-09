package com.example.homework18.presentation.user_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import com.example.homework18.domain.user_details.usecase.UserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsUseCase: UserDetailsUseCase
) : ViewModel() {

    private val _userDetails = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userDetails: StateFlow<Resource<User>> = _userDetails.asStateFlow()

    fun loadUserDetails(userId: Int) {
        viewModelScope.launch {
            userDetailsUseCase(userId = userId).collect {
                _userDetails.value = it
            }
        }
    }
}