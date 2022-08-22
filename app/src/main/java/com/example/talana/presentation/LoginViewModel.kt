package com.example.talana.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.talana.R
import com.example.talana.repository.LoginRepository
import com.example.talana.repository.RepositoryPreferences
import com.example.talana.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {
    private lateinit var repoPrefs : RepositoryPreferences
    val SUCCESS = R.raw.success
    val FAILURE = R.raw.failure
    val LOGIN = R.raw.anim_login

    fun initPrefs(context: Context){ repoPrefs = RepositoryPreferences(context)}
    fun saveApiToken(apiToken : String){ repoPrefs.saveApiKey(apiToken) }
    fun saveKeepLogin(b : Boolean) { repoPrefs.saveLoginKeep(b) }
    fun showToast(msg : String, context: Context) { Toast.makeText(context,msg,Toast.LENGTH_SHORT).show() }

    fun login(username: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(loginRepo.login(username,password)))
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class LoginViewModelFactory(private val loginRepo: LoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepository::class.java).newInstance(loginRepo)
    }
}