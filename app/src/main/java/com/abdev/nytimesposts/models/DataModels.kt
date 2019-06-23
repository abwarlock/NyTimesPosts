package com.abdev.nytimesposts.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdev.nytimesposts.utils.ResultConverters

@Entity
@TypeConverters(ResultConverters::class)
class Posts {
    var status: String? = null

    var copyright: String? = null

    @field:PrimaryKey
    var section: String = ""

    var lastUpdated: String? = null

    var numResults: Long = 0

    var results: List<Result>? = null

}

data class Result(
    val section: String? = null,
    val subsection: String? = null,
    val title: String? = null,
    val abstract: String? = null,
    val url: String? = null,
    val byline: String? = null,
    val itemType: String? = null,
    val updatedDate: String? = null,
    val createdDate: String? = null,
    val publishedDate: String? = null,
    val materialTypeFacet: String? = null,
    val kicker: String? = null,
    val desFacet: List<String>,
    val orgFacet: List<String>,
    val perFacet: List<String>,
    val geoFacet: List<String>,
    val multimedia: List<Multimedia>,
    val shortURL: String? = null
)

data class Multimedia(
    val url: String? = null,
    val format: String? = null,
    val height: Long,
    val width: Long,
    val type: String? = null,
    val subtype: String? = null,
    val caption: String? = null,
    val copyright: String? = null
)