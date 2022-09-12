package com.matis.allegroapi.di

import com.matis.allegroapi.data.sources.SearchApi
import com.matis.allegroapi.data.sources.SearchRepository
import com.matis.allegroapi.data.sources.SearchRepositoryImpl
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
    fun provideSearchRepository(searchApi: SearchApi) : SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }

    @Provides
    @Singleton
    fun provideAllegroSearchApi(): SearchApi {
        return Retrofit.Builder()
            .baseUrl("https://allegro.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}