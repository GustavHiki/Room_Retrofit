package com.example.room_retrofit.network

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

data class PostNetworkEntity(
    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Long = 0,
    @Expose
    var title: String,
    @Expose
    var body: String? = null,
    @Expose
    var images: List<String> ?= emptyList()
)
