package com.example.talana.data.remote.datasource

import com.example.talana.data.local.models.LoginRequest
import com.example.talana.data.remote.api.APIService

class LoginDataSource(private val apiService: APIService) {
    suspend fun doLogin(username: String, password: String) =
        apiService.doLogin(LoginRequest(username, password))
}