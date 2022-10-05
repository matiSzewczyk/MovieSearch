package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.Discover.Discover
import retrofit2.Response

interface SearchRepository {
    suspend fun getRecentTrendingMovies(): Response<Discover>
    suspend fun getRecentTrendingTvShows(): Response<Discover>
}