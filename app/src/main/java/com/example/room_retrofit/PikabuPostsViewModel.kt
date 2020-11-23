package com.example.room_retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PikabuPostsViewModel : ViewModel() {

    private var pikabuPosts: LiveData<List<PikabuPostModel>> = MutableLiveData()

    fun getPikabuPosts(): LiveData<List<PikabuPostModel>> {
        return pikabuPosts
    }

    fun getLiveDataPostsCountInDb(): LiveData<Long> {
        return PostsRepository.getLiveDataPostsCountInDb()
    }

    fun loadPosts(countPostsInDb: Long) {
        pikabuPosts = PostsRepository.loadPosts(countPostsInDb)
    }

    fun loadPostsFromDb() {
        pikabuPosts = PostsRepository.getPostsFromDb()
    }

    fun loadPostsFromInternet() {
        pikabuPosts = PostsRepository.getAndSavePostsFromInternet()
    }

    fun insertPostInDb(post: PikabuPostModel) {
        PostsRepository.insertPostToDb(post)
    }

    fun deletePostFromDb(id: Long) {
        PostsRepository.deletePostFromDb(id)
    }

    fun setViewedPost(id: Long?, isViewed: Boolean) {
        PostsRepository.updateViewedPost(id, isViewed)
    }

    fun isPostInDb(id: Long): LiveData<Boolean> {
        return PostsRepository.isPostInDb(id)
    }
}