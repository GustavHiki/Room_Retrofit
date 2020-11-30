package com.example.room_retrofit.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class StringsListConverter {
    @TypeConverter
    fun stringsListToJson(value: List<String>?): String {
        return if (value.isNullOrEmpty())
            "[]"
        else
            Gson().toJson(value)
    }

    @TypeConverter
    fun stringsListFromJson(value: String) = Gson().fromJson(value, Array<String>::class.java).asList()

}