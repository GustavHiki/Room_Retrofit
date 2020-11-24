package com.example.room_retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_retrofit.databinding.FragmentSavedPostsBinding

class SavedPostsFragment : Fragment(){
    private lateinit var viewModel: PikabuPostsViewModel
    private lateinit var binding: FragmentSavedPostsBinding
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
        initAdapter()
        viewModel.loadPostsFromDb()
        observeOnPosts()
    }

    private fun observeOnPosts() {
        viewModel.getPikabuPosts().observe(viewLifecycleOwner, {
            if (it != null) {
                val utils = PostDiffUtils(pikabuPostAdapter.postsList, it)
                val diffResult = DiffUtil.calculateDiff(utils)
                pikabuPostAdapter.setPosts(it)
                diffResult.dispatchUpdatesTo(pikabuPostAdapter)
            }
        })
    }

    private fun initAdapter() {
        binding.rvSavedPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSavedPosts.setHasFixedSize(true)
        pikabuPostAdapter = PikabuPostAdapter(requireFragmentManager())
        binding.rvSavedPosts.adapter = pikabuPostAdapter

    }

//    private fun initAdapter(posts: List<PikabuPostModel>) {
//        binding.rvSavedPosts.layoutManager = LinearLayoutManager(context)
//        binding.rvSavedPosts.setHasFixedSize(true)
//        pikabuPostAdapter = PikabuPostAdapter(requireContext(), posts, requireFragmentManager())
//
//        binding.rvSavedPosts.adapter = pikabuPostAdapter
//    }
}