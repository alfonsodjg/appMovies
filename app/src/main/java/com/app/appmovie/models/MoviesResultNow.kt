package com.app.appmovie.models

data class MoviesResultNow(
    val dates: Dates,
    val page: Int,
    val results: List<MoviesNow>,
    val total_pages: Int,
    val total_results: Int
)