package com.abdalrizky.githubuser.database.favorite

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorite_user")
    fun getAllUserFavorite(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(user: FavoriteUser)

    @Query("DELETE FROM favorite_user WHERE favorite_user.login = :username")
    suspend fun deleteFromFavorite(username: String): Int

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.login = :username")
    suspend fun checkUserFavorite(username: String): Int
}