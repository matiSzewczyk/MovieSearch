package com.matis.movieapp.data.sources

import com.matis.movieapp.BuildConfig
import com.matis.movieapp.data.models.Discover.Discover
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface HomeApi {

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate" +
                "&with_original_language=en"
    )
    suspend fun getRecentTrendingMovies(): Response<Discover>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "discover/tv?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate" +
                "&with_original_language=en"
    )
    suspend fun getRecentTrendingTvShows(): Response<Discover>
}