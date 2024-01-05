package com.example.hw18.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw18.databinding.UserItemBinding
import com.example.hw18.domain.model.User

class UsersAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onItemClick)
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User, click: (Int) -> Unit) {
            with(binding) {
                itemView.setOnClickListener { click(model.id) }
                Glide.with(ivUser.context).load(model.avatar).into(ivUser)
                tvEmail.text = model.email
                tvFirstName.text = model.firstName
                tvLastName.text = model.lastName
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
