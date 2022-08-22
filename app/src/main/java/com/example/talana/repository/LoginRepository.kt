package com.example.talana.repository

import com.example.talana.data.local.models.LoginResponse

interface LoginRepository {
    suspend fun login (username: String, password: String): LoginResponse
}