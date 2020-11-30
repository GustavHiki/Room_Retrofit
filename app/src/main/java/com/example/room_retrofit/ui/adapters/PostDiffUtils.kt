package com.example.room_retrofit.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.room_retrofit.models.PikabuPostModel

class PostDiffUtils(val oldList: List<PikabuPostModel>, val newList: List<PikabuPostModel>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList [oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].equals(newList[newItemPosition])
    }
}