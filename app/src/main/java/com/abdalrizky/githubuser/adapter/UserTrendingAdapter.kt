package com.abdalrizky.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.databinding.ItemUserTrendingBinding
import com.abdalrizky.githubuser.model.TrendingUserResponse
import com.bumptech.glide.Glide

class UserTrendingAdapter(private val listUser: List<TrendingUserResponse>): RecyclerView.Adapter<UserTrendingAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TrendingUserResponse)
    }

    inner class UserViewHolder(val binding: ItemUserTrendingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.apply {
            Glide.with(root.context).load(user.avatar).into(civUser)
            tvUserRank.text = holder.itemView.context.getString(R.string.rank, user.rank)
            tvName.text = user.name
            tvUsername.text = user.username
        }
        holder.binding.root.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    override fun getItemCount(): Int = listUser.size
}