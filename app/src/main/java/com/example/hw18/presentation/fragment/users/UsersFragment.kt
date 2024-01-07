package com.example.hw18.presentation.fragment.users

import android.annotation.SuppressLint
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
import com.example.hw18.presentation.model.UserUiModel
import com.example.hw18.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private val adapter = UsersAdapter(
        onItemClick = { user, position ->
            if (isSelectionModeOn()) {
                selectItem(user, position)
            } else {
                openUserDetail(user.id)
            }
        },
        onLongClick = { user, position ->
            if (!isSelectionModeOn()) {
                selectItem(user, position)
                viewModel.selectionMode(start = true)
            }
        }
    )

    private fun isSelectionModeOn(): Boolean {
        return viewModel.state.value.isSelectionModeOn
    }

    private fun selectItem(user: UserUiModel, position: Int) {
        user.isSelected = !user.isSelected
        adapter.notifyItemChanged(position)
        viewModel.selectItem(user.id, select = user.isSelected)
    }

    override fun setUp() {
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = adapter
    }

    override fun listeners() {
        binding.apply {
            btnDelete.setOnClickListener {
                viewModel.deleteSelectedItems()
            }
        }
    }


    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.users)
                    binding.apply {
                        loader.isVisible = state.isLoading
                        btnDelete.isVisible = state.isSelectionModeOn
                    }
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