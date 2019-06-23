package com.abdev.nytimesposts.viewmodels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.abdev.nytimesposts.database.DataFactory

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModels::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, DataFactory::class.java, "POSTS").build()
            @Suppress("UNCHECKED_CAST")
            return PostListViewModels(db.postDao(), activity) as T
        }
        throw IllegalStateException("Unknown ViewModel")
    }
}