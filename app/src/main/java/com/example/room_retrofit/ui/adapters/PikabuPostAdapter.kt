package com.example.room_retrofit.ui.adapters

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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.room_retrofit.models.PikabuPostModel
import com.example.room_retrofit.utils.PostDiffUtils
import com.example.room_retrofit.R
import com.example.room_retrofit.ui.PikabuPostFragment
import com.google.gson.Gson


class PikabuPostAdapter(private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<PikabuPostAdapter.PikabuPostHolder>() {

    var postsList: List<PikabuPostModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PikabuPostHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        bindHolder(holder, postsList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        if (postsList == null || postsList.isEmpty()) {
            return 0
        }
        return postsList.size
    }

    fun setPosts(posts: List<PikabuPostModel>) {
        val utils = PostDiffUtils(postsList, posts)
        val diffResult = DiffUtil.calculateDiff(utils)
        this.postsList = posts
        diffResult.dispatchUpdatesTo(this)
    }

    fun bindHolder(holder: PikabuPostHolder, currentPost: PikabuPostModel) {
        holder.id = currentPost.id
        holder.tvTitle.text = currentPost.title
        holder.tvBody.text = currentPost.body
        holder.setViewedPost(currentPost.isViewed)

        holder.llImages.removeAllViews() // cleaning container cause some images not deleted
        if (currentPost.images != null) {
            loadImagesToContainer(holder.itemView, holder.llImages, currentPost.images)
            holder.imagesUrl = currentPost.images
        }

        holder.clContainer.setOnClickListener {
            navigateToPikabuPostFragment(holder, fragmentManager)
        }
    }

    private fun navigateToPikabuPostFragment(holder: PikabuPostHolder, manager: FragmentManager) {
        val pikabuPostFragment = PikabuPostFragment()

        pikabuPostFragment.arguments = getConfigureBundle(holder)

        manager.beginTransaction()
            .replace(R.id.container, pikabuPostFragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun getConfigureBundle(holder: PikabuPostHolder): Bundle {
        val bundle = Bundle()
        bundle.putString("id", holder.id.toString())
        bundle.putString("title", holder.tvTitle.text.toString())
        bundle.putString("body", holder.tvBody.text.toString())
        bundle.putString("imagesUrl", convertImagesUrlListToJson(holder.imagesUrl))

        return bundle
    }

    private fun convertImagesUrlListToJson(imagesUrl: List<String>?): String {
        return Gson().toJson(imagesUrl)
    }

    private fun loadImagesToContainer(
        itemView: View,
        container: LinearLayout,
        images: List<String>?
    ) {
        if (images == null) return
        for (image in images) {
            val imageView = ImageView(itemView.context)
            Glide.with(itemView.context).load(image).into(imageView).clearOnDetach()
            container.addView(imageView)
        }
    }

    inner class PikabuPostHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvBody: TextView = itemView.findViewById(R.id.tv_body)
        val llImages: LinearLayout = itemView.findViewById(R.id.llImages)
        val clContainer: ConstraintLayout = itemView.findViewById(R.id.item_pikabu_post)
        var imagesUrl: List<String>? = emptyList()
        var id: Long = 0

        fun setViewedPost(isViewed: Boolean?) {
            if (isViewed == null) return

            if (isViewed) clContainer.setBackgroundResource(R.drawable.style_rounded_edges_viewed)
            else clContainer.setBackgroundResource(R.drawable.style_rounded_edges_not_viewed)
        }
    }
}