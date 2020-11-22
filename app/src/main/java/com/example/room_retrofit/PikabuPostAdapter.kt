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
    private val context: Context,
    private val posts: List<PikabuPostModel> = listOf(),
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<PikabuPostAdapter.PikabuPostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PikabuPostHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder, fragmentManager)
    }

    override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        initHolder(holder, posts.get(position))
    }

    private fun initHolder(holder: PikabuPostHolder, currentPost: PikabuPostModel) {
        holder.id = currentPost.id
        holder.tvTitle.text = currentPost.title
        holder.tvBody.text = currentPost.body
        holder.setViewedPost(currentPost.isViewed)

        holder.llImages.removeAllViews() // cleaning container cause some images not deleted
        if (currentPost.images != null) {
            loadImagesToContainer(holder.llImages, currentPost.images)
            holder.imagesUrl = currentPost.images
        }
    }

    private fun loadImagesToContainer(container: LinearLayout, images: List<String>?) {
        if (images == null)
            return
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
        if (posts == null || posts.isEmpty()) {
            return 0
        }
        return posts.size
    }

    class PikabuPostHolder(itemView: View, fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(
            itemView
        ) {

        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvBody: TextView = itemView.findViewById(R.id.tv_body)
        val llImages: LinearLayout = itemView.findViewById(R.id.llImages)
        val clContainer: ConstraintLayout = itemView.findViewById(R.id.item_pikabu_post)
        var imagesUrl: List<String>? = emptyList()
        var id: Long = 0

        init {
            clContainer.setOnClickListener {
                navigateToPikabuPostFragment(fragmentManager)
            }
        }

        fun setViewedPost(isViewed: Boolean?) {
            if (isViewed == null)
                return

            if (isViewed) clContainer.setBackgroundResource(R.drawable.style_rounded_edges_viewed)
            else clContainer.setBackgroundResource(R.drawable.style_rounded_edges_not_viewed)

        }

        private fun navigateToPikabuPostFragment(manager: FragmentManager) {
            val pikabuPostFragment = PikabuPostFragment(id)

            pikabuPostFragment.arguments = getBundle()

            manager.beginTransaction()
                .replace(R.id.container, pikabuPostFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        private fun getBundle(): Bundle {
            val bundle = Bundle()
            bundle.putString("id", id.toString())
            bundle.putString("title", tvTitle.text.toString())
            bundle.putString("body", tvBody.text.toString())
            bundle.putString("imagesUrl", convertImagesUrlListToJson())

            return bundle
        }

        private fun convertImagesUrlListToJson(): String {
            return Gson().toJson(imagesUrl)
        }
    }
}