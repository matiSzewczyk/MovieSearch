package com.matis.movieapp.di

import com.matis.movieapp.data.sources.home.HomeApi

import com.matis.movieapp.data.sources.home.HomeRepository
import com.matis.movieapp.data.sources.home.HomeRepositoryImpl
import com.matis.movieapp.utils.Constants
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
    fun provideHomeRepository(
        homeApi: HomeApi
    ): HomeRepository {
        return HomeRepositoryImpl(homeApi)
    }

    @Provides
    @Singleton
    fun provideMovieDbApi(): HomeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}