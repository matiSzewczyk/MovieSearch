package com.matis.movieapp.data.sources.home

import com.matis.movieapp.data.models.discover.Discover
import com.matis.movieapp.data.models.search.SearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {

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

    override suspend fun getTopRatedTvShows(): Response<Discover> {
        return withContext(Dispatchers.IO) {
            homeApi.getTopRatedTvShows()
        }
    }

    override suspend fun searchMovies(query: String): Response<SearchResults> {
        return withContext(Dispatchers.IO) {
            homeApi.searchForMovies(query)
        }
    }
}