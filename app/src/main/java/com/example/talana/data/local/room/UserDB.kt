package com.example.talana.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.talana.data.local.models.User


@Database(
    entities = [User::class],
    version = 5
)
abstract class UserDB : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object {

        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                UserDB::class.java,
                "contacts"
            ).allowMainThreadQueries().build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}

