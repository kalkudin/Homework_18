package com.example.homework18.presentation.user_details

import com.example.homework18.databinding.FragmentUserDetailsBinding
import com.example.homework18.presentation.common.BaseFragment

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    override fun bind() {
        val userId = arguments?.getInt("userId",)
    }

    override fun bindObservers() {
    }
}