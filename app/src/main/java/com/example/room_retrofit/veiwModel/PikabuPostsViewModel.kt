package com.example.room_retrofit.veiwModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.room_retrofit.models.PikabuPostModel

class PikabuPostsViewModel
@ViewModelInject
constructor(
    private val repository: PostsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var pikabuPosts: LiveData<List<PikabuPostModel>> = MutableLiveData()

    fun getPikabuPosts(): LiveData<List<PikabuPostModel>> = pikabuPosts


    fun getLiveDataPostsCountInDb(): LiveData<Long> = repository.getLiveDataPostsCountInDb()


    fun loadPosts(countPostsInDb: Long) {
        pikabuPosts = repository.loadPosts(countPostsInDb)
    }

    fun loadPostsFromDb() {
        pikabuPosts = repository.getPostsFromDb()
    }

    fun loadPostsFromInternet() {
        pikabuPosts = repository.getAndSavePostsFromInternet()
    }

    fun insertPostInDb(post: PikabuPostModel) {
        repository.insertPostToDb(post)
    }

    fun deletePostFromDb(id: Long) {
        repository.deletePostFromDb(id)
    }

    fun setViewedPost(id: Long?, isViewed: Boolean) {
        repository.updateViewedPost(id, isViewed)
    }

    fun isExistPostInDb(id: Long): LiveData<Boolean> = repository.isPostInDb(id)

}