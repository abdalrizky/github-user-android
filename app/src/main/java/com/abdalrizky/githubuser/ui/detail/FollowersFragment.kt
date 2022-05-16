package com.abdalrizky.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.adapter.UserAdapter
import com.abdalrizky.githubuser.databinding.FragmentFollowersBinding
import com.abdalrizky.githubuser.model.User

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFollowersBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        showLoading(true)
        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setFollowersData(user)
                showLoading(false)
            } else {
                showLoading(false)
            }
        }

        viewModel.getFollowers(username)
    }

    private fun setFollowersData(user: List<User>): Boolean {
        if (user.isEmpty()) {
            binding.nothingFollower.visibility = View.VISIBLE
            return false
        }
        val adapter = UserAdapter(user)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data)
                    startActivity(it)
                }
            }
        })
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}