package com.example.room_retrofit

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [PikabuPostModel::class], version = 1)
abstract class DataBaseRepository : RoomDatabase() {

    abstract fun postDao(): PikabuPostDao

    companion object {

        private var INSTANCE: DataBaseRepository? = null

        fun getDatabase(context: Context): DataBaseRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseRepository::class.java,
                    "pikabu_posts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}