package com.abdalrizky.githubuser.ui.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdalrizky.githubuser.api.ApiClient
import com.abdalrizky.githubuser.model.User
import com.abdalrizky.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<List<User>?>()
    val user: LiveData<List<User>?> = _user

    private val _numberOfSearches = MutableLiveData<Int?>()
    val numberOfSearches: LiveData<Int?> = _numberOfSearches

    fun getSearchUser(query: String) {
        val client = ApiClient.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _numberOfSearches.value = responseBody.totalCount
                    _user.value = responseBody.items
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _numberOfSearches.value = null
                _user.value = null
                Toast.makeText(
                    getApplication(),
                    "Gagal mengambil hasil pencarian.\nEror: ${t.message.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}