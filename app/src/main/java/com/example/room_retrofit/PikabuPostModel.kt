package com.example.room_retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
class PikabuPostModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var body: String? = "",
    var images: List<String>?
)