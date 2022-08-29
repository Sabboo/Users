package com.saber.users.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.saber.users.data.User
import com.saber.users.databinding.UserItemBinding


class UserViewHolder(private val itemBinding: UserItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(user: User?) {
        user?.let {
            itemBinding.tvUserDetails.text = user.getUserDetails()
        }
    }

}