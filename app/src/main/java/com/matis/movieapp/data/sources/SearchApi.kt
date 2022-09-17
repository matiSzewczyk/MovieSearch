package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.offerResponse.OfferResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {

    // This endpoint requires the app to be verified by Allegro.
    // Maybe take another look at this later down the road.
    @GET("offers/listing")
    suspend fun getOffers(
        @Header("Authorization") accessToken: String,
        @Query("seller.login") sellerLogin: String
    ): Response<OfferResponse>
}