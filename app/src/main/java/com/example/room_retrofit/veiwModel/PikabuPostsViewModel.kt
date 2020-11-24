package com.example.room_retrofit.veiwModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.room_retrofit.models.PikabuPostModel

class PikabuPostsViewModel : ViewModel() {

    private var pikabuPosts: LiveData<List<PikabuPostModel>> = MediatorLiveData()

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