package com.example.homework18.presentation.users

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework18.databinding.FragmentUsersListBinding
import com.example.homework18.presentation.common.BaseFragment
import com.example.homework18.presentation.model.UserPresentationModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment :
    BaseFragment<FragmentUsersListBinding>(FragmentUsersListBinding::inflate) {

    private val usersListViewModel: UsersListViewModel by viewModels()

    private val usersListRecyclerAdapter = UsersListRecyclerAdapter(
        itemClickListener = { userPresentationModel ->
            handleItemClick(userPresentationModel)
        },
        selectionListener = { userPresentationModel ->
            handleItemSelected(userPresentationModel)
        }
    )

    override fun bind() {
        bindRecyclerView()
    }

    override fun bindViewActionListeners() {
        super.bindViewActionListeners()
        bindDeleteButton()
    }

    override fun bindObservers() {
        bindLoadingState()
        bindUsers()
        bindNavigationEvents()
    }

    private fun bindRecyclerView() {
        binding.usersListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersListRecyclerAdapter
        }
    }

    private fun bindDeleteButton() {
        binding.delete.setOnClickListener {
            usersListViewModel.onEvent(UsersListEvent.DeleteSelectedUsers)
        }
    }

    private fun bindUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListViewModel.usersFlow.collect { userPresentationModels ->
                    when {
                        userPresentationModels.any { it.status == UserPresentationModel.Status.Error } -> {
                            showErrorScreen()
                        }
                        else -> {
                            usersListRecyclerAdapter.submitList(userPresentationModels)
                        }
                    }
                }
            }
        }
    }

    private fun bindNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListViewModel.navigationFlow.collect { navigationEvent ->
                    handleNavigationEvent(navigationEvent)
                }
            }
        }
    }

    private fun bindLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListViewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        showProgressBarAndHideRecyclerView()
                    } else {
                        hideProgressBarAndShowRecyclerView()
                    }
                }
            }
        }
    }

    private fun handleItemClick(user : UserPresentationModel){
        usersListViewModel.onEvent(UsersListEvent.ItemClicked(userPresentationModel = user))
    }

    private fun handleItemSelected(userPresentationModel: UserPresentationModel) {
        if (userPresentationModel.isSelected == UserPresentationModel.IsItemSelected.SELECTED) {
            usersListViewModel.onEvent(UsersListEvent.ItemSelected(userPresentationModel))
        } else {
            usersListViewModel.onEvent(UsersListEvent.ItemUnselected(userPresentationModel))
        }
    }

    private fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToDetailsPage -> navigateToUserDetailsWithAction(
                navigationEvent.userId
            )
        }
    }

    private fun navigateToUserDetailsWithAction(userId: Int) {
        val action = UsersListFragmentDirections
            .actionUsersListFragmentToUserDetailsFragment(userId)
        findNavController().navigate(action)
    }

    private fun showProgressBarAndHideRecyclerView() {
        with(binding){
            progressBar.visibility = View.VISIBLE
            usersListRecyclerView.visibility = View.GONE
        }
    }

    private fun hideProgressBarAndShowRecyclerView() {
        with(binding){
            progressBar.visibility = View.GONE
            usersListRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun showErrorScreen() {

    }
}