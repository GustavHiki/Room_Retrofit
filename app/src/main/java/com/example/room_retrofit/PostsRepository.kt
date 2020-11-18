package com.example.room_retrofit

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostsRepository {

    private lateinit var allPosts: MutableLiveData<List<PikabuPostModel>>
    private var retrofitClient = RetrofitClient.retrofitClient
    private lateinit var pikabuPostDao: PikabuPostDao

    fun getAllPosts(): MutableLiveData<List<PikabuPostModel>> {
        if (checkIsDataOnDevice()) {
            return getAllPostsFromRoom()
        } else {
            return getAllPostsFromInternet()
        }
    }

    private fun getAllPostsFromInternet(): MutableLiveData<List<PikabuPostModel>> {
        var result: MutableLiveData<List<PikabuPostModel>> = MutableLiveData()
        result.value = listOf()

        retrofitClient.getAllPosts().enqueue(object : Callback<List<PikabuPostModel>> {
            override fun onResponse(
                call: Call<List<PikabuPostModel>>,
                response: Response<List<PikabuPostModel>>
            ) {
                result.value = response.body()
            }

            override fun onFailure(call: Call<List<PikabuPostModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return result
    }

    private fun getAllPostsFromRoom(): MutableLiveData<List<PikabuPostModel>> {
        var result = MutableLiveData<List<PikabuPostModel>>()
        result.value = pikabuPostDao.getAllPosts()
        return result
    }

    private fun checkIsDataOnDevice(): Boolean {
        return getAllPostsFromRoom().value == null
    }

    fun setPikabuPostDao(postDao: PikabuPostDao) {
        pikabuPostDao = postDao
    }

    suspend fun insertAllEntityToRoom(post: List<PikabuPostModel>) {
        pikabuPostDao.insert(post)
    }

}