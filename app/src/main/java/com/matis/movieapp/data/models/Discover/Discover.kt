package com.matis.movieapp.data.models.Discover

data class Discover(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)