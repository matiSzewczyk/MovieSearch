package com.matis.moviesearch.data.models.search

data class SearchResults(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)