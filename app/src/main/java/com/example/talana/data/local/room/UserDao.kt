package com.example.talana.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.talana.data.local.models.User


@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll() : List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getByID( id : Long ) : User

    @Update
    fun update(user : List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users : List<User>)

    @Delete
    fun delete(user: User)
}