package com.example.talana.repository

import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User

interface DetailsRepository {
    suspend fun getPostByID(id : Long ): Post
    suspend fun getUserByID(id : Long ): User
    suspend fun getFavouriteByID(id : Long ): Favourite
}