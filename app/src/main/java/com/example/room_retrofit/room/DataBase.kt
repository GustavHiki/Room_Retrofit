package com.example.room_retrofit.room

import android.content.Context
import androidx.room.*
import com.example.room_retrofit.models.PikabuPostModel

@Database(entities = [PikabuPostModel::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun postDao(): PikabuPostDao

    companion object {

        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "pikabu_posts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}