package com.abdalrizky.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdalrizky.githubuser.api.ApiClientTrending
import com.abdalrizky.githubuser.model.TrendingUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _user = MutableLiveData<List<TrendingUserResponse>?>()
    val user: LiveData<List<TrendingUserResponse>?> = _user

    fun getTrendingDeveloper() {
        val client = ApiClientTrending.getApiService().getTrendingDevelopers()
        client.enqueue(object : Callback<List<TrendingUserResponse>> {
            override fun onResponse(
                call: Call<List<TrendingUserResponse>>,
                response: Response<List<TrendingUserResponse>>
            ) {
                _user.value = response.body()
            }

            override fun onFailure(call: Call<List<TrendingUserResponse>>, t: Throwable) {
                _user.value = null
            }

        })
    }
}