package com.abdalrizky.githubuser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientTrending {

    companion object {
        fun getApiService(): ApiServiceTrending {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gh-trending-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServiceTrending::class.java)
        }
    }
}