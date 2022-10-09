package com.matis.movieapp.data.sources.details

import com.matis.movieapp.BuildConfig
import com.matis.movieapp.data.models.details.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DetailsApi {
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("tv/{id}")
    suspend fun getTvShow(
        @Path("id") id: Int
    ): Response<Details>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<Details>
}