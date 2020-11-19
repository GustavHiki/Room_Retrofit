package com.example.room_retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.anko.doAsync

object PostsRepository {
    var posts: MutableLiveData<List<PikabuPostModel>> = MutableLiveData()
    private var retrofitClient = RetrofitClient.retrofitClient
    private lateinit var pikabuPostDao: PikabuPostDao

    fun initPikabuPostDao(postDao: PikabuPostDao) {
        pikabuPostDao = postDao
    }

    fun loadPosts(countPostsInDb : Long): LiveData<List<PikabuPostModel>> {
        return if (countPostsInDb != 0L) {
            getPostsFromDb()
        } else
            getAndSavePostsFromInternet()
    }


    fun getLiveDataPostsCountInDb(): LiveData<Long> {
        return pikabuPostDao.getPostsCount()
    }

    private fun getPostsFromDb(): LiveData<List<PikabuPostModel>> {
        Log.d("test123", "getPostsFromDb")

        return pikabuPostDao.getAllPosts()
    }

    private fun getAndSavePostsFromInternet(): MutableLiveData<List<PikabuPostModel>> {
        Log.d("test123", "getAllPostsFromInternet")
        val result: MutableLiveData<List<PikabuPostModel>> = MutableLiveData()

        retrofitClient.getPosts().enqueue(object : Callback<List<PikabuPostModel>> {
            override fun onResponse(call: Call<List<PikabuPostModel>>, response: Response<List<PikabuPostModel>>) {
                result.value = response.body()
                insertPostsToDb(response.body()!!)
            }

            override fun onFailure(call: Call<List<PikabuPostModel>>, t: Throwable) {
                Log.d("test123", "OnFail")
            }
        })

        return result
    }

    fun insertPostsToDb(posts: List<PikabuPostModel>) {
        doAsync {
            pikabuPostDao.insertAll(posts)
        }
    }

}