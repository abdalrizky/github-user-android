package com.abdalrizky.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abdalrizky.githubuser.database.favorite.FavoriteUser
import com.abdalrizky.githubuser.database.favorite.FavoriteUserDao
import com.abdalrizky.githubuser.database.favorite.FavoriteUserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUserDatabase: FavoriteUserDatabase = FavoriteUserDatabase.getDatabase(application)
    private var favoriteUserDao: FavoriteUserDao = favoriteUserDatabase.favoriteUserDao()

    fun getAllUserFavorite(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getAllUserFavorite()
    }
}