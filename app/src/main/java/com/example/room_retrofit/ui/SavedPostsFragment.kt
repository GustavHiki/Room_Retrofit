package com.example.room_retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.room_retrofit.databinding.FragmentSavedPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedPostsFragment : BasePostFragment() {
    private lateinit var binding: FragmentSavedPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(binding.rvSavedPosts)
        viewModel.loadPostsFromDb()
        observeOnPosts()
    }
}