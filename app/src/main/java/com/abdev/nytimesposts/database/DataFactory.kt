package com.abdev.nytimesposts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdev.nytimesposts.daos.PostDao
import com.abdev.nytimesposts.models.Posts
import com.abdev.nytimesposts.utils.ResultConverters

@Database(entities = [Posts::class], version = 1)
@TypeConverters(ResultConverters::class)
abstract class DataFactory : RoomDatabase() {

    abstract fun postDao(): PostDao

}