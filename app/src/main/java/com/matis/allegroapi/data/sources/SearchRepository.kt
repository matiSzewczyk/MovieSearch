package com.matis.allegroapi.data.sources

import com.matis.allegroapi.data.models.accessToken.TokenResponse
import com.matis.allegroapi.data.models.offerResponse.OfferResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun getAccessToken(): Response<TokenResponse>
    suspend fun getOffers(
        accessToken: String,
        sellerLogin: String
    ): Response<OfferResponse>
}