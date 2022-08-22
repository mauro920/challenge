package com.example.talana.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.talana.data.local.models.Post


@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAll() : List<Post>

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getByID( id : Long ) : Post

    @Update
    fun update(post : List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<Post>)

    @Delete
    fun delete(post: Post)
}