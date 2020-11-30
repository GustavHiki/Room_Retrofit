package com.example.room_retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.room_retrofit.R
import com.example.room_retrofit.databinding.FragmentMainPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : BasePostFragment() {
    private lateinit var binding: FragmentMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeOnPostsCountInDb()

        initAdapter(binding.recyclerView)
        viewModel.loadPostsFromInternet()
        observeOnPosts()
        setOnClickListenerOnButtons()
    }

    private fun setOnClickListenerOnButtons() {
        binding.btnSavedPostFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
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
}