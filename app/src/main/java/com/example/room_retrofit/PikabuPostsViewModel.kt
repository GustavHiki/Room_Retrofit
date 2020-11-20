package com.example.room_retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PikabuPostsViewModel : ViewModel() {

    private var pikabuPosts: LiveData<List<PikabuPostModel>> = MutableLiveData()
    private var savedPikabuPosts: LiveData<List<PikabuPostModel>> = MutableLiveData()
    private var isExistPostInDb: LiveData<Boolean> = MutableLiveData()

    fun getPikabuPosts(): LiveData<List<PikabuPostModel>> {
        return pikabuPosts
    }

    fun getSavedPikabuPosts(): LiveData<List<PikabuPostModel>> {
        return savedPikabuPosts
    }

//    fun loadPosts(countPostsInDb : Long){
//        pikabuPosts = PostsRepository.loadPosts(countPostsInDb)
//    }

    fun loadPostsFromDb(){
        savedPikabuPosts = PostsRepository.getPostsFromDb()
    }

    fun loadPostsFromInternet(){
        pikabuPosts = PostsRepository.getAndSavePostsFromInternet()
    }

    fun loadPostIsExist(id: Long){
        isExistPostInDb = PostsRepository.isExistPostInDb(id)
    }
    fun isExistPostInDb(id: Long): LiveData<Boolean>{
        return isExistPostInDb
    }

    fun getLiveDataPostsCountInDb(): LiveData<Long>{
        return PostsRepository.getLiveDataPostsCountInDb()
    }

    fun setViewedPost(id: Long?, isViewed: Boolean){
        PostsRepository.updateViewedPost(id, isViewed)
    }

    fun savePost(post: PikabuPostModel){
        PostsRepository.insertPostToDb(post)
    }

    fun deletePost(id: Long){
        PostsRepository.deletePost(id)
    }

}