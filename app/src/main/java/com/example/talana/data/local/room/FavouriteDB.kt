package com.example.talana.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.talana.data.local.models.Favourite

@Database(
    entities = [Favourite::class],
    version = 5
)
abstract class FavouriteDB : RoomDatabase() {

    abstract fun favouriteDao() : FavouriteDao

    companion object {

        private var INSTANCE: FavouriteDB? = null

        fun getDatabase(context: Context): FavouriteDB {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                FavouriteDB::class.java,
                "favourites"
            ).allowMainThreadQueries().build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
