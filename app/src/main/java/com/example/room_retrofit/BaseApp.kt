package com.example.room_retrofit

import android.app.Application

class BaseApp: Application() {
    val database by lazy { DataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        PostsRepository.initPikabuPostDao(DataBase.getDatabase(this).postDao())
    }
}