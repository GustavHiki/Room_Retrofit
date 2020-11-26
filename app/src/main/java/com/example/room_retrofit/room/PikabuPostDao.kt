package com.example.room_retrofit.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PikabuPostDao {

    @Query("SELECT * FROM post_table ORDER BY id")
    fun getAllPosts(): List<PostCacheEntity>

    @Query("SELECT COUNT(*) FROM post_table ")
    fun getPostsCount(): LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postEntity: PostCacheEntity)

    @Query("UPDATE post_table SET isViewed = :isViewed WHERE id = :id")
    fun updateViewedPost(id: Long, isViewed: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(postEntity: List<PostCacheEntity>)

    @Query("DELETE FROM post_table")
    fun deleteAll()

    @Query("DELETE FROM post_table WHERE id = :id")
    fun deletePostById(id: Long)

    @Query("SELECT EXISTS(SELECT id FROM post_table WHERE id = :id)")
    fun isExistPostInDb(id: Long): LiveData<Boolean>

}