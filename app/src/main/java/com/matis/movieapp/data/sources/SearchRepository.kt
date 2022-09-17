package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.accessToken.TokenResponse
import com.matis.movieapp.data.models.offerResponse.OfferResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun getAccessToken(): Response<TokenResponse>
    suspend fun getOffers(
        accessToken: String,
        sellerLogin: String
    ): Response<OfferResponse>
}