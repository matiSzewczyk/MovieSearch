package com.matis.movieapp.data.sources

import com.matis.movieapp.BuildConfig
import com.matis.movieapp.data.models.offerResponse.OfferResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface SearchApi {

    // This endpoint requires the app to be verified by Allegro.
    // Maybe take another look at this later down the road.
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("offers/listing")
    suspend fun getOffers(
    ): Response<OfferResponse>
}