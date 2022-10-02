package com.matis.movieapp.di

import com.matis.movieapp.data.sources.HomeApi

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
class HomeModule {

    @Provides
    @Singleton
    fun provideSearchRepository(
        homeApi: HomeApi
    ) : SearchRepository {
        return SearchRepositoryImpl(homeApi)
    }

    @Provides
    @Singleton
    fun provideAllegroSearchApi(): HomeApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}