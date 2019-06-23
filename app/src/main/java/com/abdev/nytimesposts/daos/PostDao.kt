package com.abdev.nytimesposts.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abdev.nytimesposts.models.Posts

@Dao
interface PostDao {
    @get:Query("select * FROM posts")
    val getAll: List<Posts>

    @Insert
    fun InsertAll(vararg posts: Posts)
}