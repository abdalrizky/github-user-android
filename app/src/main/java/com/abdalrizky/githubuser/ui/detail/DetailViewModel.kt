package com.abdalrizky.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdalrizky.githubuser.api.ApiClient
import com.abdalrizky.githubuser.database.favorite.FavoriteUser
import com.abdalrizky.githubuser.database.favorite.FavoriteUserDao
import com.abdalrizky.githubuser.database.favorite.FavoriteUserDatabase
import com.abdalrizky.githubuser.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<DetailUserResponse?>()
    val user: LiveData<DetailUserResponse?> = _user

    private var favoriteUserDatabase: FavoriteUserDatabase =
        FavoriteUserDatabase.getDatabase(application)
    private var favoriteUserDao: FavoriteUserDao = favoriteUserDatabase.favoriteUserDao()

    fun getDetailUser(username: String) {
        val client = ApiClient.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _user.value = null
            }
        })
    }

    fun addToFavorite(username: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, avatarUrl)
            favoriteUserDao.addToFavorite(user)
        }
    }

    fun deleteFromFavorite(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao.deleteFromFavorite(username)
        }
    }

    suspend fun checkUserFavorite(username: String) = favoriteUserDao.checkUserFavorite(username)
}