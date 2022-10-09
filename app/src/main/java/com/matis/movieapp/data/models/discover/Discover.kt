package com.matis.movieapp.data.models.discover

data class Discover(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)