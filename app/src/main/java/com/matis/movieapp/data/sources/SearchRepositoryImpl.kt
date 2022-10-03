package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.discoverTvShows.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : SearchRepository {

    override suspend fun getRecentTrendingMovies(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getRecentTrendingMovies()
        }
    }

    override suspend fun getRecentTrendingTvShows(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getRecentTrendingTvShows()
        }
    }
}