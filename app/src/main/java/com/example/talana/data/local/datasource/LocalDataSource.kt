package com.example.talana.data.local.datasource

import android.content.Context
import androidx.room.Room
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.User
import com.example.talana.data.local.room.*


class LocalDataSource(
    private val postDao: PostDao,
    private val favouriteDao: FavouriteDao,
    private val userDao: UserDao
) {

    /**
     * Posts Instance
     */


    fun getAll(): List<Post> {
        return postDao.getAll()
    }

    fun getPostByID(id: Long): Post {
        return postDao.getByID(id)
    }

    fun insertPost(listPost: List<Post>) {
        postDao.insert(listPost)
    }

    fun updatePost(listPost: List<Post>) {
        postDao.update(listPost)
    }

    /**
     * Contacts Instance
     */

    fun getAllUser(): List<User> {
        return userDao.getAll()
    }

    fun getUserByID(id: Long): User {
        return userDao.getByID(id)
    }

    fun insertUser(listUser: List<User>) {
        userDao.insert(listUser)
    }

    fun updateUser(listUser: List<User>) {
        userDao.update(listUser)
    }

    /**
     * Favourites Instance
     */

    fun getAllFavs(): List<Favourite> {
        return favouriteDao.getAll()
    }

    fun getFavByID(id: Long): Favourite {
        return favouriteDao.getByID(id)
    }

    fun insertFav(favourite: Favourite) {
        favouriteDao.insert(favourite)
    }

    fun updateFav(favourite: Favourite) {
        favouriteDao.update(favourite)
    }

    fun deleteFav(favourite: Favourite) {
        favouriteDao.delete(favourite)
    }
}