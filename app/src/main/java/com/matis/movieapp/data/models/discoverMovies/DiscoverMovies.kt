package com.matis.movieapp.data.models.discoverMovies

data class DiscoverMovies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)