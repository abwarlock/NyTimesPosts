package com.abdev.nytimesposts.utils

import androidx.room.TypeConverter
import com.abdev.nytimesposts.models.Multimedia
import com.abdev.nytimesposts.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResultConverters {
    @TypeConverter
    fun fromString(value: String): List<Result> {
        val listType = object : TypeToken<List<Result>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Result>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


class MultimediaConverters {
    @TypeConverter
    fun fromString(value: String): ArrayList<Multimedia> {
        val listType = object : TypeToken<ArrayList<Multimedia>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Multimedia>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class StringConverters {
    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


