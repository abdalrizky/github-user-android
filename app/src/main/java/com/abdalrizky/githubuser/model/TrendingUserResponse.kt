package com.abdalrizky.githubuser.model

import com.google.gson.annotations.SerializedName

data class TrendingUserResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("username")
	val username: String
)