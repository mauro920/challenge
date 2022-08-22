package com.example.talana.data.remote.datasource

import com.example.talana.data.local.models.FavouriteResponse
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User
import com.example.talana.data.remote.api.APIService

class FeedRemoteDataSource(private val apiService: APIService) {
    suspend fun getPosts(): List<Post> = apiService.getPosts()
    suspend fun addFav(apiKey:String, id: Long): FavouriteResponse = apiService.addFav(apiKey, id)
    suspend fun getUsers(): List<User> = apiService.getUsers()
}