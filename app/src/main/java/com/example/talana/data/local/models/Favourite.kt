package com.example.talana.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id_post")val id_post: Long,
)


data class FavouriteResponse(
    @SerializedName("STATUS")val status: String,
)
