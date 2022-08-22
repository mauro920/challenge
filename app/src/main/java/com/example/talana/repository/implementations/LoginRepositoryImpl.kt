package com.example.talana.repository.implementations

import com.example.talana.data.local.models.LoginResponse
import com.example.talana.data.remote.datasource.LoginDataSource
import com.example.talana.repository.LoginRepository

class LoginRepositoryImpl(private val dataSource: LoginDataSource): LoginRepository {
    override suspend fun login(username: String, password: String): LoginResponse = dataSource.doLogin(username, password)
}