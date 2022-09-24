package com.matis.movieapp.data.sources

import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
     private val searchApi: SearchApi,
 ) : SearchRepository {

//     override suspend fun getOffers(
//         accessToken: String,
//         sellerLogin: String
//     ): Response<OfferResponse> {
//         return withContext(Dispatchers.IO) {
//             searchApi.getOffers(
//                 "Bearer $accessToken",
//                 sellerLogin
//             )
//         }
 }