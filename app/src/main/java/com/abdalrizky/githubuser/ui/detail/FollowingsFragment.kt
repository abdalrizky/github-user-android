package com.abdalrizky.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.adapter.UserAdapter
import com.abdalrizky.githubuser.databinding.FragmentFollowingsBinding
import com.abdalrizky.githubuser.model.User

class FollowingsFragment : Fragment(R.layout.fragment_followings) {

    private lateinit var binding: FragmentFollowingsBinding
    private lateinit var viewModel: FollowingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFollowingsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        showLoading(true)
        viewModel = ViewModelProvider(this).get(FollowingsViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setFollowingsData(user)
                showLoading(false)
            }
        }

        viewModel.getFollowings(username)

    }

    private fun setFollowingsData(user: List<User>): Boolean {
        if (user.isEmpty()) {
            binding.nothingFollowing.visibility = View.VISIBLE
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