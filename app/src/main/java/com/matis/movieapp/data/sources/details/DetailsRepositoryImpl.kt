package com.matis.movieapp.data.sources.details

import com.matis.movieapp.data.models.Details.movie.DetailsMovie
import com.matis.movieapp.data.models.Details.tvShow.DetailsTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi
) : DetailsRepository {

    override suspend fun getTvShow(id: Int): Response<DetailsTvShow> {
        return withContext(Dispatchers.IO) {
            detailsApi.getTvShow(id)
        }
    }

    override suspend fun getMovie(id: Int): Response<DetailsMovie> {
        return withContext(Dispatchers.IO) {
            detailsApi.getMovie(id)
        }
    }
}