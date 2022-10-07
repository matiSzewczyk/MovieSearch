package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.Discover.Discover
import retrofit2.Response

interface SearchRepository {
    suspend fun getTrendingMovies(): Response<Discover>
    suspend fun getTrendingTvShows(): Response<Discover>
}