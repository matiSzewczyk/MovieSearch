package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.discoverMovies.DiscoverMovies
import com.matis.movieapp.data.models.discoverTvShows.DiscoverTvShows
import retrofit2.Response

interface SearchRepository {
    suspend fun getRecentTrendingMovies(): Response<DiscoverMovies>
    suspend fun getRecentTrendingTvShows(): Response<DiscoverTvShows>
}