package com.example.room_retrofit

import android.os.Bundle
import android.util.Log
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
    private val fragmentManager: FragmentManager
) :
RecyclerView.Adapter<PikabuPostAdapter.PikabuPostHolder>(){

    var postsList: List<PikabuPostModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PikabuPostHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pikabu_post, parent, false)
        return PikabuPostHolder(viewHolder, fragmentManager)
    }

	override fun onBindViewHolder(holder: PikabuPostHolder, position: Int) {
        holder.bind(postsList[position])
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

    fun setPosts(posts: List<PikabuPostModel>){
        this.postsList = posts
    }

	class PikabuPostHolder(itemView: View, fragmentManager: FragmentManager) :
		RecyclerView.ViewHolder(itemView) {

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

        fun bind(currentPost: PikabuPostModel) {
            id = currentPost.id
            tvTitle.text = currentPost.title
            tvBody.text = currentPost.body
            setViewedPost(currentPost.isViewed)

            llImages.removeAllViews() // cleaning container cause some images not deleted
            if (currentPost.images != null) {
                loadImagesToContainer(llImages, currentPost.images)
                imagesUrl = currentPost.images
            }
        }

        private fun loadImagesToContainer(container: LinearLayout, images: List<String>?) {
            if (images == null)
                return
            for (image in images) {
                val imageView = ImageView(itemView.context)
                Glide
                    .with(itemView.context)
                    .load(image)
                    .into(imageView).clearOnDetach()
                container.addView(imageView)
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