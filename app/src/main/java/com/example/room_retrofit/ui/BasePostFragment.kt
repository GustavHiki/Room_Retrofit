package com.example.room_retrofit.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room_retrofit.ui.adapters.PikabuPostAdapter
import com.example.room_retrofit.veiwModel.PikabuPostsViewModel

open class BasePostFragment : Fragment() {
    val viewModel: PikabuPostsViewModel by viewModels()
    lateinit var pikabuPostAdapter: PikabuPostAdapter

    fun initAdapter(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        pikabuPostAdapter = PikabuPostAdapter(parentFragmentManager)
        recyclerView.adapter = pikabuPostAdapter
    }

    fun observeOnPosts() {
        viewModel.getPikabuPosts().observe(viewLifecycleOwner, {
            if (it != null) {
                pikabuPostAdapter.setPosts(it)
            }
        })
    }
}