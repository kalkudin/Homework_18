package com.example.homework18.presentation.mapper

import com.example.homework18.domain.model.User
import com.example.homework18.presentation.model.UserPresentationModel

fun User.toPresentationModel() : UserPresentationModel{
    return UserPresentationModel(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar,
        isSelected = UserPresentationModel.IsItemSelected.NOT_SELECTED
    )
}