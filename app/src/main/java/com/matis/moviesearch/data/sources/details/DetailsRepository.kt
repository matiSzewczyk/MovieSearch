package com.matis.moviesearch.data.sources.details

import com.matis.moviesearch.data.models.details.Details
import retrofit2.Response

interface DetailsRepository {
    suspend fun getTvShow(id: Int): Response<Details>
    suspend fun getMovie(id: Int): Response<Details>
}