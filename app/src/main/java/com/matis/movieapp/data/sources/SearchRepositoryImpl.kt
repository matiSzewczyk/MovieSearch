package com.matis.movieapp.data.sources

import com.matis.movieapp.data.models.accessToken.TokenResponse
import com.matis.movieapp.data.models.offerResponse.OfferResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
     private val searchApi: SearchApi,
     private val tokenApi: TokenApi
 ) : SearchRepository {

     override suspend fun getAccessToken(): Response<TokenResponse> {
         return withContext(Dispatchers.IO) {
             tokenApi.getAccessToken()
         }
     }

     override suspend fun getOffers(
         accessToken: String,
         sellerLogin: String
     ): Response<OfferResponse> {
         return withContext(Dispatchers.IO) {
             searchApi.getOffers(
                 "Bearer $accessToken",
                 sellerLogin
             )
         }
     }
 }