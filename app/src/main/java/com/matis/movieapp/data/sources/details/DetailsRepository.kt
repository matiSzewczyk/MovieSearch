package com.matis.movieapp.data.sources.details

import com.matis.movieapp.data.models.Details.movie.DetailsMovie
import com.matis.movieapp.data.models.Details.tvShow.DetailsTvShow
import retrofit2.Response

interface DetailsRepository {
    suspend fun getTvShow(id: Int): Response<DetailsTvShow>
    suspend fun getMovie(id: Int): Response<DetailsMovie>
}