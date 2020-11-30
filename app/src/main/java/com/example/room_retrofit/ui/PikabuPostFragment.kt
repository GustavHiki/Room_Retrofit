package com.example.room_retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.room_retrofit.models.PikabuPostModel
import com.example.room_retrofit.databinding.FragmentPikabuPostBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PikabuPostFragment : BasePostFragment() {

    private var postId: Long? = 0
    private lateinit var binding: FragmentPikabuPostBinding
    private lateinit var imagesUrl: List<String>
    private lateinit var isExistPostInDb: LiveData<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPikabuPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(arguments)
        observeOnSwitchState()
        setOnClickListenerOnButtons()
    }

    private fun observeOnSwitchState() {
        if (postId != null)
            isExistPostInDb = viewModel.isExistPostInDb(postId!!)

        isExistPostInDb.observe(viewLifecycleOwner, {
            binding.swSaveOrDelete.isChecked = it
        })
    }

    private fun setOnClickListenerOnButtons() {
        binding.swSaveOrDelete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.insertPostInDb(
                    PikabuPostModel(
                        postId!!,
                        binding.tvTitlePost.text.toString(),
                        binding.tvBodyPost.text.toString(),
                        true,
                        imagesUrl
                    )
                )
            } else {
                viewModel.deletePostFromDb(postId!!)
            }
        }
    }

    private fun initView(bundle: Bundle?) {
        if (bundle == null)
            return
        postId = bundle.getString("id")?.toLong()
        viewModel.setViewedPost(postId, true)
        binding.tvTitlePost.text = bundle.getString("title", "")
        binding.tvBodyPost.text = bundle.getString("body", "")
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