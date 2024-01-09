package com.example.homework18.presentation.users

import com.example.homework18.presentation.model.UserPresentationModel

sealed class UsersListEvent(){
    data class ItemClicked(val userPresentationModel : UserPresentationModel) : UsersListEvent()
    data class ItemSelected(val userPresentationModel: UserPresentationModel) : UsersListEvent()
    data class ItemUnselected(val userPresentationModel: UserPresentationModel) : UsersListEvent()
    data object DeleteSelectedUsers : UsersListEvent()
    data object RefreshPage : UsersListEvent()
}