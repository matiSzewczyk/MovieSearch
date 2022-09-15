package com.matis.allegroapi.data.sources

import com.matis.allegroapi.data.models.accessToken.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

 class SearchRepositoryImpl @Inject constructor(
     private val searchApi: SearchApi
 ) : SearchRepository {
     override suspend fun getAccessToken(): Response<TokenResponse> {
         return withContext(Dispatchers.IO) {
             searchApi.getAccessToken()
         }
     }
 }