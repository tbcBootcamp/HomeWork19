package com.example.hw18.presentation.fragment.details

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.hw18.databinding.FragmentUserDetailsBinding
import com.example.hw18.presentation.fragment.BaseFragment
import com.example.hw18.utils.setImage
import com.example.hw18.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val viewModel: UserDetailsViewModel by viewModels()
    private val args: UserDetailsFragmentArgs by navArgs()

    override fun setUp() {
        val userId = args.userId
        viewModel.getUserDetails(userId)
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.user?.let {
                        binding.apply {
                            tvEmail.text = it.email
                            tvFirstName.text = it.firstName
                            tvLastName.text = it.lastName
                            ivUser.setImage(it.avatar)
                        }
                    }
                    binding.loader.isVisible = state.isLoading
                    binding.group.isVisible = !state.isLoading
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    toast(error)
                }
            }
        }
    }
}