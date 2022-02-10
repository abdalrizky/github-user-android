package com.abdalrizky.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdalrizky.githubuser.api.ApiClient
import com.abdalrizky.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingsViewModel : ViewModel() {
    private val _user = MutableLiveData<List<User>?>()
    val user: LiveData<List<User>?> = _user

    fun getFollowings(username: String) {
        val client = ApiClient.getApiService().getFollowings(username)
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