package com.example.talana.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.talana.data.local.models.Post


@Database(
    entities = [Post::class],
    version = 5
)
abstract class PostDB : RoomDatabase() {

    abstract fun postDao() : PostDao

    companion object {

        private var INSTANCE: PostDB? = null

        fun getDatabase(context: Context): PostDB {
            INSTANCE = INSTANCE ?:  Room.databaseBuilder(
                context.applicationContext,
                PostDB::class.java,
                "feed"
            ).allowMainThreadQueries().build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}