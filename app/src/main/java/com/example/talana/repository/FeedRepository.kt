package com.example.talana.repository

import androidx.lifecycle.LiveData
import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.FavouriteResponse
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User

interface FeedRepository {
    suspend fun getPosts(): List<Post>
    suspend fun addFavourite(apiKey: String, id: Long): FavouriteResponse
    fun getFavourites(): List<Favourite>
    suspend fun getUsers(): List<User>
    fun deleteFavourite(id: Long)

}