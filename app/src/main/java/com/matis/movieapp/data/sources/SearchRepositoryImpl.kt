package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.discoverMovies.DiscoverMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
) : SearchRepository {

    override suspend fun getRecentTrendingMovies(): Response<DiscoverMovies> {
        return withContext(Dispatchers.IO) {
            searchApi.getRecentTrendingMovies()
        }
    }
}