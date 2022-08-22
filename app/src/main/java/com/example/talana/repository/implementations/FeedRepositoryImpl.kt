package com.example.talana.repository.implementations

import com.example.talana.data.local.datasource.LocalDataSource
import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.FavouriteResponse
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User
import com.example.talana.data.remote.datasource.FeedRemoteDataSource
import com.example.talana.repository.FeedRepository
import com.example.talana.utils.InternetCheck

class FeedRepositoryImpl(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: LocalDataSource
) : FeedRepository {

    override suspend fun getPosts(): List<Post> {
        return if (InternetCheck.isNetworkAvailable()) {
            localDataSource.insertPost(remoteDataSource.getPosts())
            localDataSource.getAll()
        } else {
            localDataSource.getAll()
        }
    }

    override suspend fun addFavourite(apiKey: String, id: Long): FavouriteResponse {
        if (remoteDataSource.addFav(apiKey, id) == FavouriteResponse("OK")){
            localDataSource.insertFav(Favourite(id))
        }
        return remoteDataSource.addFav(apiKey, id)
    }

    override fun deleteFavourite(id: Long) = localDataSource.deleteFav(Favourite(id))

    override fun getFavourites(): List<Favourite> = localDataSource.getAllFavs()

    override suspend fun getUsers(): List<User> {
        return if (InternetCheck.isNetworkAvailable()) {
            localDataSource.insertUser(remoteDataSource.getUsers())
            localDataSource.getAllUser()
        } else {
            localDataSource.getAllUser()
        }
    }
}
