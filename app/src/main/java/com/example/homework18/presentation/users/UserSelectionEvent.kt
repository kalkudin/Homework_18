package com.example.homework18.presentation.users

import com.example.homework18.presentation.model.UserPresentationModel

sealed class UserSelectionEvent {
    data class ItemSelected(val userPresentationModel: UserPresentationModel) : UserSelectionEvent()
    data class ItemUnselected(val userPresentationModel: UserPresentationModel) : UserSelectionEvent()
}