package com.example.talana.presentation

import androidx.lifecycle.*
import com.example.talana.data.local.models.Favourite
import com.example.talana.repository.DetailsRepository
import com.example.talana.repository.FeedRepository
import com.example.talana.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DetailsViewModel(private val detailsRepo: DetailsRepository) : ViewModel() {

    fun fetchPostInfo(id: Long) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(detailsRepo.getPostByID(id)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchUserInfo(id: Long) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(detailsRepo.getUserByID(id)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchIsFavourite(id: Long) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(detailsRepo.getFavouriteByID(id)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

class DetailsViewModelFactory(private val detailsRepo: DetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DetailsRepository::class.java).newInstance(detailsRepo)
    }
}
