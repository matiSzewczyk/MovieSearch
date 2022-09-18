package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.accessToken.TokenResponse
import com.matis.movieapp.data.models.offerResponse.OfferResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun getOffers(
        sellerLogin: String
    ): Response<OfferResponse>
}