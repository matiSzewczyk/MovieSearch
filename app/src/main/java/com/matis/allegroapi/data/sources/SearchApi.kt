package com.matis.allegroapi.data.sources

import com.matis.allegroapi.BuildConfig
import com.matis.allegroapi.data.models.accessToken.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface SearchApi {

    @Headers("Authorization: Basic ${BuildConfig.BASE64}")
    @GET("auth/oauth/token")
    suspend fun getAccessToken(): Response<TokenResponse>
}