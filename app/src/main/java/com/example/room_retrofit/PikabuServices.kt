package com.example.room_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface PikabuServices {

    @GET("page/interview/mobile-app/test-api/feed.php")
    fun getPosts(): Call<List<PikabuPostModel>>
}