package com.example.room_retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.room_retrofit.databinding.FragmentPikabuPostBinding
import com.google.gson.Gson

class PikabuPostFragment(private var postId: Long) : Fragment() {

    private lateinit var binding: FragmentPikabuPostBinding
    private lateinit var viewModel: PikabuPostsViewModel

    private lateinit var imagesUrl: List<String>
//    private var postId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPikabuPostBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(PikabuPostsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(arguments)
        buttonsSetOnClickListener()
    }

    private fun buttonsSetOnClickListener() {
        binding.btnSaveToDb.setOnClickListener {
            viewModel.insertPostInDb(
                PikabuPostModel(
                    postId,
                    binding.tvTitlePost.text.toString(),
                    binding.tvBodyPost.text.toString(),
                    true,
                    imagesUrl
                )
            )
        }

        binding.btnDeleteFromDb.setOnClickListener {
            viewModel.deletePostFromDb(postId)
        }
    }

    private fun initView(bundle: Bundle?) {
        if (bundle == null)
            return
        viewModel.setViewedPost(bundle.getString("id")?.toLong(), true)
        binding.tvTitlePost.text = bundle.getString("title")
        binding.tvBodyPost.text = bundle.getString("body")
        imagesUrl = getStringsListFromJson(bundle.getString("imagesUrl"))
        loadImagesToContainer(binding.imagesContainer, imagesUrl)
    }

    private fun getStringsListFromJson(value: String?) =
        Gson().fromJson(value, Array<String>::class.java).toList()

    private fun loadImagesToContainer(container: LinearLayout, images: List<String>) {
        for (image in images) {
            val imageView = ImageView(requireContext())
            Glide
                .with(requireContext())
                .load(image)
                .into(imageView)
            container.addView(imageView)
        }
    }


}