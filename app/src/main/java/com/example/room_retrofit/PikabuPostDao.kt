package com.example.room_retrofit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PikabuPostDao {

    @Query("SELECT * FROM post_table ORDER BY id")
    fun getAllPosts(): LiveData<List<PikabuPostModel>>

    @Query("SELECT COUNT(*) FROM post_table ")
    fun getPostsCount(): LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postModel: PikabuPostModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(postModel: List<PikabuPostModel>)

    @Query("DELETE FROM post_table")
    fun deleteAll()
}