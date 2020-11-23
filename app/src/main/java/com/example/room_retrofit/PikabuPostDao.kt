package com.example.room_retrofit

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PikabuPostDao {

    @Query("SELECT * FROM post_table ORDER BY id")
    fun getAllPosts(): LiveData<List<PikabuPostModel>>

    @Query("SELECT COUNT(*) FROM post_table ")
    fun getPostsCount(): LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postModel: PikabuPostModel)

    @Query("UPDATE post_table SET isViewed = :isViewed WHERE id = :id")
    fun updateViewedPost(id: Long, isViewed: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(postModel: List<PikabuPostModel>)

    @Query("DELETE FROM post_table")
    fun deleteAll()

    @Query("DELETE FROM post_table WHERE id = :id")
    fun deletePostById(id: Long)

    @Query("SELECT EXISTS(SELECT id FROM post_table WHERE id = :id)")
    fun isPostInDb(id: Long): LiveData<Boolean>

}