package com.example.homework18.presentation.users

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework18.data.common.Resource
import com.example.homework18.databinding.FragmentUsersListBinding
import com.example.homework18.domain.model.User
import com.example.homework18.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : BaseFragment<FragmentUsersListBinding>(FragmentUsersListBinding::inflate){

    private val usersListViewModel : UsersListViewModel by viewModels()

    private val usersListRecyclerAdapter = UsersListRecyclerAdapter { user ->
        handleItemClick(user)
    }

    override fun bind() {
        bindRecyclerView()
    }

    override fun bindObservers() {
        bindUsers()
        bindNavigationEvents()
    }

    private fun bindRecyclerView(){
        binding.usersListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersListRecyclerAdapter
        }
    }

    private fun bindUsers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListViewModel.usersFlow.collect {
                    when(it){
                        is Resource.Success -> usersListRecyclerAdapter.submitList(it.data)
                        is Resource.Error -> showErrorScreen()
                        else -> doNothing()
                    }
                }
            }
        }
    }

    private fun bindNavigationEvents(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListViewModel.navigationFlow.collect { navigationEvent ->
                    handleNavigationEvent(navigationEvent)
                }
            }
        }
    }

    private fun handleItemClick(user : User){
        usersListViewModel.onClick(user)
    }

    private fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToDetailsPage -> navigateToUserDetailsWithAction(navigationEvent.userId)
            else -> {
                doNothing()
            }
        }
    }

    private fun navigateToUserDetailsWithAction(userId: Int) {
        val action = UsersListFragmentDirections
            .actionUsersListFragmentToUserDetailsFragment(userId)
        findNavController().navigate(action)
    }

    private fun showErrorScreen(){

    }

    private fun doNothing(){

    }
}