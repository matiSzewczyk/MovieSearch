package com.matis.allegroapi.data.sources

import com.matis.allegroapi.data.models.accessToken.TokenResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun getAccessToken(): Response<TokenResponse>
}