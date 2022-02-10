package com.abdalrizky.githubuser.api

import com.abdalrizky.githubuser.model.TrendingUserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceTrending {
    @GET("/developers")
    fun getTrendingDevelopers(): Call<List<TrendingUserResponse>>
}