package com.abdalrizky.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.adapter.UserAdapter
import com.abdalrizky.githubuser.database.favorite.FavoriteUser
import com.abdalrizky.githubuser.databinding.FragmentFavoriteBinding
import com.abdalrizky.githubuser.model.User
import com.abdalrizky.githubuser.ui.detail.DetailActivity

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFavoriteBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        viewModel.getAllUserFavorite().observe(viewLifecycleOwner) {
            if (it != null) {
                val users = mapList(it)
                setFavoriteData(users)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): List<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(user.login, user.avatarUrl)
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun setFavoriteData(user: List<User>): Boolean {
        if (user.isEmpty()) {
            binding.rvUser.visibility = View.INVISIBLE
            binding.nothingFavorite.visibility = View.VISIBLE
            return false
        }
        adapter = UserAdapter(user)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data)
                    startActivity(it)
                }
            }
        })
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(context)

        return true
    }
}