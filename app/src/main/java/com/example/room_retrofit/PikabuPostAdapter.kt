package com.example.room_retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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
            .inflate(R.layout.pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        val currentPost = posts?.get(position)
        holder.tvBody.text = currentPost?.body
        holder.tvTitle.text = currentPost?.title

        holder.llImages.removeAllViews()
        if (currentPost?.images != null) {
            for (url in currentPost.images!!) {
                var iv = ImageView(context)
                Glide
                    .with(context)
                    .load(url)
                    .into(iv)
                holder.llImages.addView(iv)
            }
        }

    }

    override fun getItemCount(): Int {
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

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvBody = itemView.findViewById(R.id.tv_body)
//            imageView = itemView.findViewById(R.id.imageView)
            llImages = itemView.findViewById(R.id.llImages)
        }
    }
}