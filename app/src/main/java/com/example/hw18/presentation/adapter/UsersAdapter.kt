package com.example.hw18.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw18.databinding.UserItemBinding
import com.example.hw18.presentation.model.UserUiModel

class UsersAdapter(
    private val onItemClick: (UserUiModel, Int) -> Unit,
    private val onLongClick: (UserUiModel, Int) -> Unit
) :
    ListAdapter<UserUiModel, UsersAdapter.UserViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onItemClick, onLongClick, position)
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: UserUiModel,
            click: (UserUiModel, Int) -> Unit,
            longClick: (UserUiModel, Int) -> Unit,
            position: Int
        ) {
            with(binding) {
                itemView.setOnClickListener { click(model, position) }
                itemView.setOnLongClickListener {
                    longClick(model, position)
                    true
                }
                Glide.with(ivUser.context).load(model.avatar).into(ivUser)
                tvEmail.text = model.email
                tvFirstName.text = model.firstName
                tvLastName.text = model.lastName
                ivCheck.isVisible = model.isSelected
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<UserUiModel>() {
        override fun areItemsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
            return false //oldItem == newItem
        }
    }
}