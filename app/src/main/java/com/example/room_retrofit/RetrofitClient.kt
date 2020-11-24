package com.example.room_retrofit

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private val baseUrl = "https://pikabu.ru/"
    var retrofitClient: PikabuServices
//    var rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    init{
        retrofitClient = getClient(baseUrl).create(PikabuServices::class.java)
    }

    private var retrofit: Retrofit? = null

    private fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(rxAdapter)
                .build()
        }
        return retrofit!!
    }
}