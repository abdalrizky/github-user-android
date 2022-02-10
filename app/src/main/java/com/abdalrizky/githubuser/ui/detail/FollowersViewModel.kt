package com.abdalrizky.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdalrizky.githubuser.api.ApiClient
import com.abdalrizky.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<List<User>?>()
    val user: LiveData<List<User>?> = _user

    fun getFollowers(username: String) {
        val client = ApiClient.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _user.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _user.value = null
            }

        })
    }
}