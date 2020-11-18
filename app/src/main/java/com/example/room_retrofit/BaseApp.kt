package com.example.room_retrofit

import android.app.Application

class BaseApp: Application() {
    val database by lazy { DataBaseRepository.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        PostsRepository.setPikabuPostDao(database.postDao())
    }
}