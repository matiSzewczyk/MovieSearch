package com.matis.moviesearch.data.sources.home

import com.matis.moviesearch.BuildConfig
import com.matis.moviesearch.data.models.discover.Discover
import com.matis.moviesearch.data.models.search.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

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

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET(
        "search/movie?language=en-US"
    )
    suspend fun searchForMovies(
        @Query("query") query: String
    ): Response<SearchResults>
}