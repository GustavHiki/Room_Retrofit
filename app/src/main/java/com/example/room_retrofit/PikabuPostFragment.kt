package com.example.room_retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.room_retrofit.databinding.FragmentPikabuPostBinding
import com.google.gson.Gson

class PikabuPostFragment : Fragment() {

    private lateinit var binding: FragmentPikabuPostBinding
    private lateinit var viewModel: PikabuPostsViewModel
    private var postId: Long = 0

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
//        if(postId != null)
            viewModel.loadPostIsExist(postId)
        observeOnExistPostInDb()
        initView(arguments)
    }

    private fun initView(bundle: Bundle?) {
        if (bundle == null)
            return
        postId = bundle.getString("id")?.toLong()!!
        viewModel.setViewedPost(postId, true)
        binding.tvTitlePost.text = bundle.getString("title")
        binding.tvBodyPost.text = bundle.getString("body")


//        binding.swSaveOrDelete.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                viewModel.savePost(
//                    PikabuPostModel(
//                        postId!!,
//                        binding.tvTitlePost.text.toString(),
//                        binding.tvBodyPost.text.toString(),
//                        true,
//                        getStringsListFromJson(bundle.getString("imagesUrl"))))
//            } else {
//                //TODO delete post
//                viewModel.deletePost(postId!!)
//
//            }
//        }

        loadImagesToContainer(
            binding.imagesContainer,
            getStringsListFromJson(bundle.getString("imagesUrl"))
        )
    }

    private fun observeOnExistPostInDb() {
        val isExistPostInDb = viewModel.isExistPostInDb(postId!!)

        isExistPostInDb.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                Log.d("test123", "isExist " + value)
//                binding.swSaveOrDelete.isChecked = value
//                isExistPostInDb.removeObserver(this)
            }
        })
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