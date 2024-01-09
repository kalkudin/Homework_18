package com.example.homework18.presentation.model

data class UserPresentationModel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    var isSelected: IsItemSelected,
){
    enum class IsItemSelected{
        SELECTED,
        NOT_SELECTED
    }
}