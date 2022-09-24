package com.matis.movieapp.di

import com.matis.movieapp.data.sources.SearchApi
import com.matis.movieapp.data.sources.SearchRepository
import com.matis.movieapp.data.sources.SearchRepositoryImpl
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
        searchApi: SearchApi
    ) : SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }

    @Provides
    @Singleton
    fun provideAllegroSearchApi(): SearchApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}