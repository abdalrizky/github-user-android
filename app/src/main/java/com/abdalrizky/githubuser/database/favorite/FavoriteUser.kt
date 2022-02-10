package com.abdalrizky.githubuser.database.favorite

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey
    val login: String,
    val avatarUrl: String,
)