package com.example.talana.presentation

import android.content.Context
import androidx.lifecycle.*
import com.example.talana.data.local.models.Favourite
import com.example.talana.repository.FeedRepository
import com.example.talana.repository.RepositoryPreferences
import com.example.talana.repository.implementations.FeedRepositoryImpl
import com.example.talana.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedViewModel(private val feedRepo: FeedRepository) : ViewModel() {
    val favsPost = MutableLiveData<List<Favourite>>()


    fun fetchPosts() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(feedRepo.getPosts()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun addFav(apikey: String, id: Long) =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(feedRepo.addFavourite(apikey, id)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun deleteFav( id: Long) =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(feedRepo.deleteFavourite(id)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun fetchFavs() {
        favsPost.postValue(feedRepo.getFavourites())
    }

    fun fetchUsers() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(feedRepo.getUsers()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }



}

class FeedViewModelFactory(private val feedRepo: FeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FeedRepository::class.java).newInstance(feedRepo)
    }
}
