package com.example.hw18.presentation.fragment.users

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw18.databinding.FragmentUsersBinding
import com.example.hw18.presentation.adapter.UsersAdapter
import com.example.hw18.presentation.fragment.BaseFragment
import com.example.hw18.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private val adapter = UsersAdapter { userId -> openUserDetail(userId) }

    override fun setUp() {
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = adapter

    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.users)
                    binding.loader.isVisible = state.isLoading
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    toast(error)
                }
            }
        }
    }

    private fun openUserDetail(userId: Int) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(
                userId
            )
        )
    }
}