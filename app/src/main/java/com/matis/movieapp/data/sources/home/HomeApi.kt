package com.matis.movieapp.data.sources.home

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
    suspend fun getTrendingMovies(): Response<Discover>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "discover/tv?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate" +
                "&with_original_language=en"
    )
    suspend fun getTrendingTvShows(): Response<Discover>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "movie/top_rated?language=en-US"
    )
    suspend fun getTopRatedMovies(): Response<Discover>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "tv/top_rated?language=en-US"
    )
    suspend fun getTopRatedTvShows(): Response<Discover>
}