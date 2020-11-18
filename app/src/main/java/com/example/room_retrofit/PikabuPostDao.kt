package com.example.room_retrofit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PikabuPostDao {

    @Query("SELECT * FROM post_table ORDER BY id")
    fun getAllPosts(): List<PikabuPostModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(postModel: PikabuPostModel)

//    @Query("INSERT INTO post_table $postModel")
//    suspend fun insert(postModel: PikabuPostModel)

    @Query("DELETE FROM post_table")
    suspend fun deleteAll()
}