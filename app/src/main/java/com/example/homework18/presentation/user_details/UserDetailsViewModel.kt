package com.example.homework18.presentation.user_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework18.data.common.Resource
import com.example.homework18.domain.model.User
import com.example.homework18.domain.user_details.repository.UserDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {

    private val _userDetails = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userDetails: StateFlow<Resource<User>> = _userDetails.asStateFlow()

    fun loadUserDetails(userId: Int) {
        Log.d("UserDetailsVM", "Loading user details for ID: $userId")
        viewModelScope.launch {
            userDetailsRepository.getUserDetails(userId).collect {
                Log.d("UserDetailsVM", "User details updated: $it")
                _userDetails.value = it
            }
        }
    }
}