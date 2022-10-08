package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.Discover.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : SearchRepository {

    override suspend fun getTrendingMovies(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getTrendingMovies()
        }
    }

    override suspend fun getTrendingTvShows(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getTrendingTvShows()
        }
    }

    override suspend fun getTopRatedMovies(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getTopRatedMovies()
        }
    }
}