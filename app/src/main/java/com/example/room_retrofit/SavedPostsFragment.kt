package com.example.room_retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_retrofit.databinding.FragmentSavedPostsBinding

class SavedPostsFragment : Fragment(){

    private lateinit var binding: FragmentSavedPostsBinding
    private lateinit var viewModel: PikabuPostsViewModel
    private lateinit var pikabuPostAdapter: PikabuPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedPostsBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(PikabuPostsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPostsFromDb()
        observeOnSavedPosts()

//        if(viewModel.getSavedPikabuPosts().value != null)
//            initAdapter(viewModel.getSavedPikabuPosts().value!!)
    }

    private fun observeOnSavedPosts() {
        viewModel.getSavedPikabuPosts().observe(viewLifecycleOwner, {
//            if (it != null) {
                initAdapter(it)
//            }
        })
    }
    private fun initAdapter(savedPosts: List<PikabuPostModel>) {
        binding.recyclerViewSavedPosts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSavedPosts.setHasFixedSize(true)
        pikabuPostAdapter = PikabuPostAdapter(requireContext(), savedPosts, requireFragmentManager())
        binding.recyclerViewSavedPosts.adapter = pikabuPostAdapter
    }

}