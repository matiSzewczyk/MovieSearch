package com.matis.allegroapi.data.sources

interface SearchRepository {
    suspend fun getAccessToken()
}