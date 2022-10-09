package com.matis.movieapp.data.sources.details

import com.matis.movieapp.data.models.details.movie.DetailsMovie
import retrofit2.Response

interface DetailsRepository {
    suspend fun getTvShow(id: Int): Response<DetailsMovie>
    suspend fun getMovie(id: Int): Response<DetailsMovie>
}