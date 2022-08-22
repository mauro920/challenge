package com.example.talana.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.talana.data.local.models.Favourite


@Dao
interface FavouriteDao {
    @Query("SELECT * FROM Favourite")
    fun getAll() : List<Favourite>

    @Query("SELECT * FROM Favourite WHERE id_post = :id")
    fun getByID( id : Long ) : Favourite

    @Update
    fun update(favourite : Favourite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite : Favourite)

    @Delete
    fun delete(favourite: Favourite)
}