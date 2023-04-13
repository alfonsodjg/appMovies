package com.app.appmovie.models

data class MovieTopDbResult(
    val page: Int,
    val results: List<MoviesTop>,
    val total_pages: Int,
    val total_results: Int
)