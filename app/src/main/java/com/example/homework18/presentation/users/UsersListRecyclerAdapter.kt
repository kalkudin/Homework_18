package com.example.homework18.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework18.databinding.UsersListItemLayoutBinding
import com.example.homework18.domain.model.User

class UsersListRecyclerAdapter(private val itemClickListener: (User) -> Unit) :
    ListAdapter<User, UsersListRecyclerAdapter.UserViewHolder>(UserDiffCallback()) {

    inner class UserViewHolder(private val binding: UsersListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.id.text = user.id.toString()
            binding.email.text = user.email
            binding.firstName.text = user.firstName
            binding.lastName.text = user.lastName

            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.avatar)

            itemView.setOnClickListener {
                itemClickListener.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            UsersListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
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
