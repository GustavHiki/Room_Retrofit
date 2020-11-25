package com.example.room_retrofit.network

import android.content.Context
import androidx.room.Room
import com.example.room_retrofit.room.DataBase
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var pikabuServices: PikabuServices
    private const val baseUrl = "https://pikabu.ru/"
    private var retrofit: Retrofit ?= null

    init{
//        retrofit = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
        pikabuServices = getClient().create(PikabuServices::class.java)
    }

    fun getClient(): Retrofit {
        return RetrofitClient.retrofit ?: synchronized(this) {
            val instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            RetrofitClient.retrofit  = instance
            instance
        }
    }
}