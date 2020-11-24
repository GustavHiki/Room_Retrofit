package com.example.room_retrofit

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface PikabuServices {

    @GET("page/interview/mobile-app/test-api/feed.php")
    fun getPosts(): Flowable<List<PikabuPostModel>>
}