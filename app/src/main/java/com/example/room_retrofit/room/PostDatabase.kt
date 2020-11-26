package com.example.room_retrofit.room

import androidx.room.*


@Database(entities = [PostCacheEntity::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PikabuPostDao

    companion object {
        const val DATABASE_NAME = "pikabu_posts_database"
    }
}