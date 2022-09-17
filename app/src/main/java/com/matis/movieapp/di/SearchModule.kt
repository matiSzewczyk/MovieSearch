package com.matis.movieapp.di

import com.matis.movieapp.data.sources.SearchApi
import com.matis.movieapp.data.sources.SearchRepository
import com.matis.movieapp.data.sources.SearchRepositoryImpl
import com.matis.movieapp.data.sources.TokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchModule {

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchApi: SearchApi,
        tokenApi: TokenApi
    ) : SearchRepository {
        return SearchRepositoryImpl(searchApi, tokenApi)
    }

    @Provides
    @Singleton
    fun provideAllegroSearchApi(): SearchApi {
        return Retrofit.Builder()
            .baseUrl("https://api.allegro.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}