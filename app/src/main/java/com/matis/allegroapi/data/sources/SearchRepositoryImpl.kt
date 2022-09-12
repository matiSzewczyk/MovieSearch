package com.matis.allegroapi.data.sources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

 class SearchRepositoryImpl @Inject constructor(
     private val searchApi: SearchApi
 ) : SearchRepository {
     override suspend fun getAccessToken() {
         return withContext(Dispatchers.IO) {
             searchApi.getAccessToken()
         }
     }
 }