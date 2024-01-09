package com.example.homework18.presentation.model

data class UserPresentationModel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    var isSelected: IsItemSelected,
    var status: Status = Status.Success
) {
    enum class IsItemSelected{
        SELECTED,
        NOT_SELECTED
    }

    enum class Status {
        Loading, Success, Error
    }
}