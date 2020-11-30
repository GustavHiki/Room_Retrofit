package com.example.room_retrofit.veiwModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams.fromPublisher
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.room_retrofit.models.PikabuPostModel
import com.example.room_retrofit.network.IPikabuServices
import com.example.room_retrofit.network.NetworkMapper
import com.example.room_retrofit.network.PostNetworkEntity
import com.example.room_retrofit.room.PikabuPostDao
import com.example.room_retrofit.room.CacheMapper
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class PostsRepository
    constructor(
    private val pikabuPostDao: PikabuPostDao,
    private val pikabuServices: IPikabuServices,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
    ) {

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

        val result = MutableLiveData<List<PikabuPostModel>>()
        doAsync {
            result.postValue(cacheMapper.mapFromEntityList(pikabuPostDao.getAllPosts()))
        }

        return result
    }

    fun getAndSavePostsFromInternet(): LiveData<List<PikabuPostModel>> {
        Log.i("test123", "getAllPostsFromInternet")
        val result: MediatorLiveData<List<PikabuPostModel>> = MediatorLiveData()

        val sourse: LiveData<List<PostNetworkEntity>> = fromPublisher(
            pikabuServices.getPosts()
                .subscribeOn(Schedulers.io())
        )

        result.addSource(sourse) {
            result.postValue(networkMapper.mapFromEntityList(it))
            result.removeSource(sourse)
//            insertPostsToDb(it)
        }

        return result
    }

    fun insertPostsToDb(posts: List<PikabuPostModel>?) {
        if (posts == null)
            return
        doAsync {
            pikabuPostDao.insertAll(cacheMapper.mapToEntityList(posts) )
        }
    }

    fun insertPostToDb(post: PikabuPostModel?) {
        if (post == null)
            return
        doAsync {
            pikabuPostDao.insert(cacheMapper.mapToEntity(post))
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
        return pikabuPostDao.isExistPostInDb(id)
    }

}