package com.example.homework18.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework18.databinding.UsersListItemLayoutBinding
import com.example.homework18.domain.model.User
import com.example.homework18.presentation.model.UserPresentationModel

class UsersListRecyclerAdapter(
    private val itemClickListener: (UserPresentationModel) -> Unit,
    private val selectionListener: (UserPresentationModel) -> Unit
) : ListAdapter<UserPresentationModel, UsersListRecyclerAdapter.UserViewHolder>(
    UserPresentationModelDiffCallback()
) {

    inner class UserViewHolder(private val binding: UsersListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userPresentationModel: UserPresentationModel) {
            with(binding) {
                id.text = userPresentationModel.id.toString()
                email.text = userPresentationModel.email
                firstName.text = userPresentationModel.firstName
                lastName.text = userPresentationModel.lastName

                Glide.with(itemView.context)
                    .load(userPresentationModel.avatar)
                    .into(avatar)

                itemView.setOnClickListener {
                    itemClickListener.invoke(userPresentationModel)
                }

                itemView.alpha =
                    if (userPresentationModel.isSelected == UserPresentationModel.IsItemSelected.SELECTED) 0.5f else 1.0f

                itemView.setOnLongClickListener {
                    userPresentationModel.isSelected =
                        if (userPresentationModel.isSelected == UserPresentationModel.IsItemSelected.SELECTED)
                            UserPresentationModel.IsItemSelected.NOT_SELECTED
                        else
                            UserPresentationModel.IsItemSelected.SELECTED
                    selectionListener.invoke(userPresentationModel)
                    notifyItemChanged(adapterPosition)
                    true
                }
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

    private class UserPresentationModelDiffCallback :
        DiffUtil.ItemCallback<UserPresentationModel>() {
        override fun areItemsTheSame(
            oldItem: UserPresentationModel,
            newItem: UserPresentationModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserPresentationModel,
            newItem: UserPresentationModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
