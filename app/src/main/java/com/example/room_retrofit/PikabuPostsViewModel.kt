package com.example.room_retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PikabuPostsViewModel : ViewModel() {

    private var allPikabuPosts: MutableLiveData<List<PikabuPostModel>> = MutableLiveData()

    init {
        allPikabuPosts = PostsRepository.getAllPosts()
    }

    fun getAllPikabuPosts(): LiveData<List<PikabuPostModel>> {
        return allPikabuPosts
    }

    fun saveData(){
        PostsRepository.
    }

    fun setAllPikabuPosts(allPikabuPosts: List<PikabuPostModel>) {
        this.allPikabuPosts.value = allPikabuPosts
    }
}