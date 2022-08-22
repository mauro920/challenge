package com.example.talana.data.local.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)

data class LoginResponse(
    @SerializedName("STATUS") var STATUS: String,
    @SerializedName("api-token") var apiToken: String
)