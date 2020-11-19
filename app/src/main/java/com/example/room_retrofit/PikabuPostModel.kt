package com.example.room_retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "post_table")
@TypeConverters(StringsListConverter::class)
class PikabuPostModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var body: String? = "",
    var images: List<String> ?= emptyList()
)