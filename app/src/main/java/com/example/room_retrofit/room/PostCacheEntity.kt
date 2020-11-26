package com.example.room_retrofit.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.room_retrofit.utils.StringsListConverter

@Entity(tableName = "post_table")
@TypeConverters(StringsListConverter::class)
data class PostCacheEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,
    var title: String,
    var body: String? = null,
    var isViewed: Boolean? = false,
    var images: List<String>? = emptyList()
)
