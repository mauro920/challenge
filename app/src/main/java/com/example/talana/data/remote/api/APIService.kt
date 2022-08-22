package com.example.talana.data.remote.api

import com.example.talana.BuildConfig
import com.example.talana.data.local.models.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIService {

    @POST("/api/login/")
    suspend fun doLogin(
        @Body user: LoginRequest
    ): LoginResponse

    @POST("/api/favorite/")
    @FormUrlEncoded
    suspend fun addFav(@Header("apikey")apiKey:String, @Field("postid") id: Long): FavouriteResponse

    @GET("/api/feed/")
    suspend fun getPosts(): List<Post>

    @GET("/api/contacts/")
    suspend fun getUsers(): List<User>

}

object RetrofitClient {
    val apiservice by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(APIService::class.java)
    }
}
