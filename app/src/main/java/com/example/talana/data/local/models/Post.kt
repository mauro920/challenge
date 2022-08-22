package com.example.talana.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

@Entity
data class Post (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")val id: Long,
    @SerializedName("title")val title: String,
    @SerializedName("link")val link: String?,
    @SerializedName("image")val image: String,
    @SerializedName("date")val date: String,
    @SerializedName("description")val description: String,
    @SerializedName("published")val published: String,
    @SerializedName("author_id")val authorID: String
)