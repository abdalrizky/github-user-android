package com.abdalrizky.githubuser.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.adapter.UserTrendingAdapter
import com.abdalrizky.githubuser.databinding.FragmentHomeBinding
import com.abdalrizky.githubuser.model.TrendingUserResponse
import com.abdalrizky.githubuser.model.User
import com.abdalrizky.githubuser.ui.detail.DetailActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setTrendingData(user)
                showLoading(false)
            } else {
                setTrendingData(null)
                showLoading(false)
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(context)
        viewModel.getTrendingDeveloper()
    }

    private fun setTrendingData(user: List<TrendingUserResponse>?): Boolean {
        if (user == null) {
            binding.tvFailure.visibility = View.VISIBLE
            return false
        }
        val adapter = UserTrendingAdapter(user)
        adapter.setOnItemClickCallback(object : UserTrendingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TrendingUserResponse) {
                val dataMapped = data.mapToUser()
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, dataMapped)
                    startActivity(it)
                }
            }
        })
        binding.rvUser.adapter = adapter
        return true
    }

    private fun TrendingUserResponse.mapToUser() = User(
        username,
        avatar
    )

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}