package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.discoverMovies.DiscoverMovies
import retrofit2.Response

interface SearchRepository {
    suspend fun getRecentTrendingMovies(): Response<DiscoverMovies>
}