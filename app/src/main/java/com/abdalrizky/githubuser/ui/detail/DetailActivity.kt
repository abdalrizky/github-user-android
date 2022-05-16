package com.abdalrizky.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.abdalrizky.githubuser.R
import com.abdalrizky.githubuser.databinding.ActivityDetailBinding
import com.abdalrizky.githubuser.model.DetailUserResponse
import com.abdalrizky.githubuser.model.User
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        setSupportActionBar(binding.appBarDetail.toolbar)
        supportActionBar?.apply {
            title = user.login
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        showLoading(true)

        viewModel.user.observe(this) {
            if (it != null) {
                setDetailData(it)
                showLoading(false)
            } else {
                showLoading(false)
                onBackPressed()
                Toast.makeText(this, "Gagal menampilkan halaman detail.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getDetailUser(user.login)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user.login)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.appBarDetail.contentDetail.viewPagerDetail.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.appBarDetail.contentDetail.tabLayoutDetail,
            binding.appBarDetail.contentDetail.viewPagerDetail
        ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUserFavorite(user.login)
            withContext(Dispatchers.Main) {
                isFavorite = if (count > 0) {
                    binding.appBarDetail.contentDetail.fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.ic_favorite
                        )
                    )
                    true
                } else {
                    binding.appBarDetail.contentDetail.fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.ic_favorite_border
                        )
                    )
                    false
                }
            }
        }

        binding.appBarDetail.contentDetail.fab.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteFromFavorite(user.login)
                binding.appBarDetail.contentDetail.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite_border
                    )
                )
                isFavorite = false
                Toast.makeText(
                    this,
                    "${user.login} berhasil dihapus dari favorit.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.addToFavorite(user.login, user.avatarUrl)
                binding.appBarDetail.contentDetail.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite
                    )
                )
                isFavorite = true
                Toast.makeText(
                    this,
                    "${user.login} berhasil ditambahkan ke favorit.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_share -> {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Temukan informasi ${binding.appBarDetail.contentDetail.tvDeveloperName.text} di aplikasi Github User atau melalui url berikut:\nhttps://github.com/${supportActionBar?.title}"
                    )
                    type = "text/plain"
                }
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDetailData(user: DetailUserResponse) {
        Glide.with(this).load(user.avatarUrl).into(binding.appBarDetail.contentDetail.civUserDetail)
        binding.appBarDetail.contentDetail.apply {
            tvDeveloperName.text = user.name
            tvDeveloperLocation.text = user.location ?: "-"
            tvDeveloperCompany.text = user.company ?: "-"
            tvRepository.text = getString(R.string._d_repository, user.publicRepos)
            tvFollowers.text = getString(R.string._d_followers, user.followers)
            tvFollowings.text = getString(R.string._d_followings, user.following)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.appBarDetail.contentDetail.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}