package com.example.homework18.presentation.user_details

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.homework18.data.common.Resource
import com.example.homework18.databinding.FragmentUserDetailsBinding
import com.example.homework18.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val userDetailsViewModel: UserDetailsViewModel by viewModels()
    override fun bind() {
        val userId = arguments?.getInt("userId")
        Log.d("UserDetailsFragment", "bind called with userid: ${userId.toString()}")
        userId?.let {
            userDetailsViewModel.loadUserDetails(it)
        }
    }

    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailsViewModel.userDetails.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val user = resource.data
                            with(binding) {
                                id.text = user.id.toString()
                                email.text = user.email
                                firstName.text = user.firstName
                                lastName.text = user.lastName
                                bindAvatar(user.avatar)
                                hideProgressBar()
                            }
                        }
                        is Resource.Error -> {
                            showErrorScreen()
                            hideProgressBar()
                        }
                        is Resource.Loading -> {
                            showProgressBar()
                        }
                    }
                }
            }
        }
    }

    private fun bindAvatar(avatarUrl: String) {
        Glide.with(this)
            .load(avatarUrl)
            .into(binding.avatar)
    }

    private fun showErrorScreen() {

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}