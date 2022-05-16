package com.abdalrizky.githubuser.database.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey
    val login: String,
    val avatarUrl: String,
)