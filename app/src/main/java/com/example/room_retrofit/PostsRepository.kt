package com.example.room_retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams.fromPublisher
import androidx.lifecycle.MediatorLiveData
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

object PostsRepository {
    private var retrofitClient = RetrofitClient.retrofitClient
    private lateinit var pikabuPostDao: PikabuPostDao

    fun initPikabuPostDao(postDao: PikabuPostDao) {
        pikabuPostDao = postDao
    }

    fun loadPosts(countPostsInDb: Long): LiveData<List<PikabuPostModel>> {
        return if (countPostsInDb != 0L) {
            getPostsFromDb()
        } else
            getAndSavePostsFromInternet()
    }


    fun getLiveDataPostsCountInDb(): LiveData<Long> {
        return pikabuPostDao.getPostsCount()
    }

    fun getPostsFromDb(): LiveData<List<PikabuPostModel>> {
        Log.i("test123", "getPostsFromDb")

        return pikabuPostDao.getAllPosts()
    }

    fun getAndSavePostsFromInternet(): LiveData<List<PikabuPostModel>> {
        Log.i("test123", "getAllPostsFromInternet")
        var result: MediatorLiveData<List<PikabuPostModel>> = MediatorLiveData()

        val sourse: LiveData<List<PikabuPostModel>> = fromPublisher(
            retrofitClient.getPosts()
                .subscribeOn(Schedulers.io())
        )

        result.addSource(sourse) {
            result.value = it
            result.removeSource(sourse)
//            insertPostsToDb(it)
        }

        return result
    }

    fun insertPostsToDb(posts: List<PikabuPostModel>?) {
        if (posts == null)
            return
        doAsync {
            pikabuPostDao.insertAll(posts)
        }
    }

    fun insertPostToDb(post: PikabuPostModel?) {
        if (post == null)
            return
        doAsync {
            pikabuPostDao.insert(post)
        }
    }

    fun deletePostFromDb(id: Long) {
        doAsync {
            pikabuPostDao.deletePostById(id)
        }
    }

    fun updateViewedPost(id: Long?, isViewed: Boolean) {
        if (id == null)
            return
        doAsync {
            pikabuPostDao.updateViewedPost(id, isViewed)
        }
    }

    fun isPostInDb(id: Long): LiveData<Boolean> {
        return pikabuPostDao.isPostInDb(id)
    }

}