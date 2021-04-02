package com.moapps.moviesnews.pojo

data class MovieDetails(
    val backdrop_path: String,
    val budget: Int,
    val id: Int,
    val original_language: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)