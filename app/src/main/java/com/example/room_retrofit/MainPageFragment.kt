package com.example.room_retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        observeOnPostsCountInDb()
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
        viewModel.getPikabuPosts().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                initAdapter(it)
            }
        })
    }

    private fun initAdapter(posts: List<PikabuPostModel>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        pikabuPostAdapter = PikabuPostAdapter(requireContext(), posts, requireFragmentManager())

        binding.recyclerView.adapter = pikabuPostAdapter
    }
}