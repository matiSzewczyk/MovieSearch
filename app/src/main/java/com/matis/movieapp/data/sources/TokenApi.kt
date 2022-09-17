package com.matis.movieapp.data.sources

import com.matis.movieapp.BuildConfig
import com.matis.movieapp.data.models.accessToken.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TokenApi {
    @Headers("Authorization: Basic ${BuildConfig.BASE64}")
    @GET("auth/oauth/token?grant_type=client_credentials")
    suspend fun getAccessToken(): Response<TokenResponse>
}