package com.example.room_retrofit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson


class PikabuPostAdapter(
    context: Context,
    posts: List<PikabuPostModel>? = listOf(),
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<PikabuPostAdapter.PikabuPostHolder>() {

    private var posts: List<PikabuPostModel>?
    private val context: Context
    private val childFragmentManager: FragmentManager

    init {
        this.posts = posts
        this.context = context
        this.childFragmentManager = childFragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PikabuPostHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder, childFragmentManager)
    }

    override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        val currentPost = posts?.get(position)
        holder.tvTitle.text = currentPost?.title
        holder.tvBody.text = currentPost?.body
        holder.imagesUrl = currentPost?.images!!

        holder.llImages.removeAllViews() // cleaning container cause some images not deleted
        //currentPost?.images != null
        if (currentPost.images != null) {
            loadImagesToContainer(holder.llImages, currentPost.images!!)
        }
    }

    private fun loadImagesToContainer(container: LinearLayout, images: List<String>) {
        for (image in images) {
            val imageView = ImageView(context)
            Glide
                .with(context)
                .load(image)
                .into(imageView)
            container.addView(imageView)
        }
    }

    override fun getItemCount(): Int {
        if (posts == null || posts?.isEmpty()!!)
            return 0
        return posts!!.size
    }

    fun setPosts(posts: List<PikabuPostModel>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    class PikabuPostHolder(itemView: View, fragmentManager: FragmentManager) : RecyclerView.ViewHolder(
        itemView
    ) {
        val tvTitle: TextView
        val tvBody: TextView
        val llImages: LinearLayout
        lateinit var imagesUrl: List<String>
        val clContainer: ConstraintLayout

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvBody = itemView.findViewById(R.id.tv_body)
            llImages = itemView.findViewById(R.id.llImages)

            clContainer = itemView.findViewById(R.id.item_pikabu_post)
            clContainer.setOnClickListener {
                navigateToPikabuPostFragment(fragmentManager)
            }
        }

        private fun navigateToPikabuPostFragment(manager: FragmentManager){
            val pikabuPostFragment = PikabuPostFragment()

            val bundle = Bundle()
            bundle.putString("title", tvTitle.text.toString())
            bundle.putString("body", tvBody.text.toString())
            bundle.putString("imagesUrl", getImagesUrlListAndConvertToJson())
            pikabuPostFragment.arguments = bundle

            manager.beginTransaction()
                .replace(R.id.container, pikabuPostFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        private fun getImagesUrlListAndConvertToJson(): String {
            return Gson().toJson(imagesUrl)

        }
    }
}