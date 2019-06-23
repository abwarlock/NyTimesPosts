package com.abdev.nytimesposts.networking

import com.abdev.nytimesposts.models.Posts
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @GET("topstories/v2/{section}.json")
    fun getPosts(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): Observable<Posts>
}