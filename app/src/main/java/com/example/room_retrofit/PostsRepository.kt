package com.example.room_retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.anko.doAsync

object PostsRepository {
    private var retrofitClient = RetrofitClient.retrofitClient
    private lateinit var pikabuPostDao: PikabuPostDao

    fun initPikabuPostDao(postDao: PikabuPostDao) {
        pikabuPostDao = postDao
    }

//    fun loadPosts(countPostsInDb : Long): LiveData<List<PikabuPostModel>> {
//        return if (countPostsInDb != 0L) {
//            getPostsFromDb()
//        } else
//            getAndSavePostsFromInternet()
//    }


    fun getLiveDataPostsCountInDb(): LiveData<Long> {
        return pikabuPostDao.getPostsCount()
    }

    fun getPostsFromDb(): LiveData<List<PikabuPostModel>> {
        Log.d("test123", "getPostsFromDb")

        return pikabuPostDao.getAllPosts()
    }

    fun getAndSavePostsFromInternet(): MutableLiveData<List<PikabuPostModel>> {
        Log.d("test123", "getAllPostsFromInternet")
        val result: MutableLiveData<List<PikabuPostModel>> = MutableLiveData()

        retrofitClient.getPosts().enqueue(object : Callback<List<PikabuPostModel>> {
            override fun onResponse(call: Call<List<PikabuPostModel>>, response: Response<List<PikabuPostModel>>) {

                result.value = getModelListInitialized(response.body())
//                insertPostsToDb(response.body())
            }
            private fun getModelListInitialized(model: List<PikabuPostModel>?): List<PikabuPostModel>{
                if (model == null)
                    return emptyList()
                model.forEach {
                    it.isViewed = false
                }
                return model
            }

            override fun onFailure(call: Call<List<PikabuPostModel>>, t: Throwable) {
            }
        })

        return result
    }

    fun insertPostsToDb(posts: List<PikabuPostModel>?) {
        if(posts == null)
            return
        doAsync {
            pikabuPostDao.insertAll(posts)
        }
    }

    fun insertPostToDb(post: PikabuPostModel?) {
        if(post == null)
            return
        doAsync {
            pikabuPostDao.insert(post)
        }
    }

    fun updateViewedPost(id: Long?, isViewed: Boolean){
        if (id == null)
            return
        doAsync {
            pikabuPostDao.updateViewedPost(id, isViewed)
        }
    }

    fun isExistPostInDb(id: Long): LiveData<Boolean>{
        var isExist: LiveData<Boolean> = MutableLiveData()
        doAsync {
            isExist = pikabuPostDao.isExistPostInDb(id)
        }
        return isExist
    }

    fun deletePost(id: Long){
        doAsync {
            pikabuPostDao.delete(id)
        }
    }

}