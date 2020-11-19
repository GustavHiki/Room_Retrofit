package com.example.room_retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PikabuPostAdapter(context: Context, posts: List<PikabuPostModel>? = listOf()) :
    RecyclerView.Adapter<PikabuPostAdapter.PikabuPostHolder>() {

    private var posts: List<PikabuPostModel>?
    private val context: Context

    init {
        this.posts = posts
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PikabuPostHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        val currentPost = posts?.get(position)
        holder.tvTitle.text = currentPost?.title
        holder.tvBody.text = currentPost?.body
        holder.llImages.removeAllViews() // cleaning container cause some images not deleted
        if (currentPost?.images != null) {
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

    class PikabuPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView
        val tvBody: TextView
        val llImages: LinearLayout
        val clContainer: ConstraintLayout

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvBody = itemView.findViewById(R.id.tv_body)
            llImages = itemView.findViewById(R.id.llImages)

            clContainer = itemView.findViewById(R.id.cl_container)
            clContainer.setOnClickListener({

            })

        }
    }
}