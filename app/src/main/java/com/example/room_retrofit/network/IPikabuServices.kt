package com.example.room_retrofit.network

import io.reactivex.Flowable
import retrofit2.http.GET

interface IPikabuServices {

    @GET("page/interview/mobile-app/test-api/feed.php")
    fun getPosts(): Flowable<List<PostNetworkEntity>>
}