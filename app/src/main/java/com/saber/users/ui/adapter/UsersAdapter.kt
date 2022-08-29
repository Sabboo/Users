package com.saber.users.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saber.users.data.User
import com.saber.users.databinding.UserItemBinding


class UsersAdapter(private val users: List<User>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<UserViewHolder>() {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.rootView.setOnClickListener { onItemClick.invoke(users[position].id) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding: UserItemBinding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = users.size

}
