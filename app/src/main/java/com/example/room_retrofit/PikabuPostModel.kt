package com.example.room_retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "post_table")
@TypeConverters(StringsListConverter::class)
class PikabuPostModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var title: String,
    var body: String? = null,
    var isViewed: Boolean? = false,
    var images: List<String> ?= emptyList()
){
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass){
            return false
        }

        other as PikabuPostModel

        if (id != other.id){
            return false
        }

        if (title != other.title){
            return false
        }

        if (body != other.body){
            return false
        }

        if (images != other.images){
            return false
        }

        return true
    }
}