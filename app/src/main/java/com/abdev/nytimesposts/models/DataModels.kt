package com.abdev.nytimesposts.models

data class Posts(
    val status: String,
    val copyright: String,
    val section: String,
    val lastUpdated: String,
    val numResults: Long,
    val results: List<Result>
)

data class Result(
    val section: String,
    val subsection: String,
    val title: String,
    val abstract: String,
    val url: String,
    val byline: String,
    val itemType: String,
    val updatedDate: String,
    val createdDate: String,
    val publishedDate: String,
    val materialTypeFacet: String,
    val kicker: String,
    val desFacet: List<String>,
    val orgFacet: List<String>,
    val perFacet: List<String>,
    val geoFacet: List<String>,
    val multimedia: List<Multimedia>,
    val shortURL: String
)

data class Multimedia(
    val url: String,
    val format: String,
    val height: Long,
    val width: Long,
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String
)