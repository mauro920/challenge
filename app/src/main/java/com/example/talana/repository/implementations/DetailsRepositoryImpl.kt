package com.example.talana.repository.implementations

import com.example.talana.data.local.datasource.LocalDataSource
import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User
import com.example.talana.repository.DetailsRepository

class DetailsRepositoryImpl(private val localDataSource: LocalDataSource): DetailsRepository {
    override suspend fun getPostByID(id: Long): Post = localDataSource.getPostByID(id)

    override suspend fun getUserByID(id: Long): User = localDataSource.getUserByID(id)

    override suspend fun getFavouriteByID(id: Long): Favourite = localDataSource.getFavByID(id)

}