package com.example.room_retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room_retrofit.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {
    private lateinit var viewModel: PikabuPostsViewModel
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var pikabuPostAdapter: PikabuPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(PikabuPostsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeOnPostsCountInDb()

        initAdapter()
        viewModel.loadPostsFromInternet()
        observeOnPosts()
        buttonsSetOnClickListener()
    }

    private fun buttonsSetOnClickListener(){
        binding.btnSavedPostFragment.setOnClickListener{
            requireFragmentManager().beginTransaction()
                .replace(R.id.container, SavedPostsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeOnPostsCountInDb() {
        val liveDataCountPostsInDb = viewModel.getLiveDataPostsCountInDb()

        liveDataCountPostsInDb.observe(viewLifecycleOwner, object : Observer<Long> {
            override fun onChanged(value: Long) {
                viewModel.loadPosts(value)
                observeOnPosts()
                liveDataCountPostsInDb.removeObserver(this)
            }
        })
    }

    private fun observeOnPosts() {
        viewModel.getPikabuPosts().observe(viewLifecycleOwner, {
            if (it != null) {
                pikabuPostAdapter.setPosts(it)
            }
        })
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        pikabuPostAdapter = PikabuPostAdapter(requireFragmentManager())
        binding.recyclerView.adapter = pikabuPostAdapter

        binding.recyclerView.isNestedScrollingEnabled = true

    }
}