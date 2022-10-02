package com.matis.movieapp.data.models.discoverTvShows

data class DiscoverTvShows(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)