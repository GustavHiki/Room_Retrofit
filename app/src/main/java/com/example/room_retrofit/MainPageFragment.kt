package com.example.room_retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_retrofit.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment() {

    private lateinit var viewModel: PikabuPostsViewModel
    private lateinit var binding: MainPageFragmentBinding
    private lateinit var pikabuPostAdapter: PikabuPostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(PikabuPostsViewModel::class.java)



        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)


        pikabuPostAdapter = PikabuPostAdapter(requireContext(), viewModel.getAllPikabuPosts().value)
        binding.recyclerView.adapter = pikabuPostAdapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllPikabuPosts().observe(this,  {
            pikabuPostAdapter.setPosts(it)
        })

    }
}