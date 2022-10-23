package com.matis.moviesearch.data.sources.home

import com.matis.moviesearch.data.models.discover.Discover
import com.matis.moviesearch.data.models.search.SearchResults
import retrofit2.Response

interface HomeRepository {
    suspend fun getTrendingMovies(): Response<Discover>
    suspend fun getTrendingTvShows(): Response<Discover>
    suspend fun getTopRatedMovies(): Response<Discover>
    suspend fun getTopRatedTvShows(): Response<Discover>
    suspend fun searchMovies(query: String): Response<SearchResults>
}