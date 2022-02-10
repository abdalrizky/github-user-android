package com.abdalrizky.githubuser.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.adapter.UserAdapter
import com.abdalrizky.githubuser.databinding.ActivitySearchBinding
import com.abdalrizky.githubuser.model.User
import com.abdalrizky.githubuser.ui.detail.DetailActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarSearch.toolbarSearch)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.numberOfSearches.observe(this, { value ->
            if (value != null) {
                setNumberOfSearches(value)
            } else {
                setNumberOfSearches(null)
            }
        })

        viewModel.user.observe(this, { user ->
            if (user != null) {
                setSearchData(user)
                showLoading(false)
            } else {
                showLoading(false)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.appBarSearch.contentSearch.rvUser.layoutManager = layoutManager

        binding.appBarSearch.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.getSearchUser(query)
    }

    private fun setNumberOfSearches(value: Int?): Boolean {
        if (value == null) {
            return false
        }
        binding.appBarSearch.contentSearch.numberOfSearch.text = getString(R.string.number_of_search, value)
        binding.appBarSearch.contentSearch.numberOfSearch.visibility = View.VISIBLE
        return true
    }

    private fun setSearchData(user: List<User>) {
        adapter = UserAdapter(user)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@SearchActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data)
                    startActivity(it)
                }
            }
        })
        binding.appBarSearch.contentSearch.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.appBarSearch.contentSearch.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}