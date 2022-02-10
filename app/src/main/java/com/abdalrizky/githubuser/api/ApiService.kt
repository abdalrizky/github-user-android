package com.abdalrizky.githubuser.api

import com.abdalrizky.githubuser.BuildConfig
import com.abdalrizky.githubuser.model.DetailUserResponse
import com.abdalrizky.githubuser.model.User
import com.abdalrizky.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/search/users")
    @Headers(BuildConfig.TOKEN_API_GITHUB)
    fun getSearchUsers(@Query("q") query: String): Call<UserResponse>

    @GET("/users/{username}")
    @Headers(BuildConfig.TOKEN_API_GITHUB)
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    @Headers(BuildConfig.TOKEN_API_GITHUB)
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("/users/{username}/following")
    @Headers(BuildConfig.TOKEN_API_GITHUB)
    fun getFollowings(@Path("username") username: String): Call<List<User>>

}