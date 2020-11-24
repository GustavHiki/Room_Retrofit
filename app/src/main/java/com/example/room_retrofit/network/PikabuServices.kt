package com.example.room_retrofit.network

import com.example.room_retrofit.models.PikabuPostModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface PikabuServices {

    @GET("page/interview/mobile-app/test-api/feed.php")
    fun getPosts(): Flowable<List<PikabuPostModel>>
}