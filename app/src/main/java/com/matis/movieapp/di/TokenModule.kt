package com.matis.movieapp.di

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
class TokenModule {

    @Provides
    @Singleton
    fun provideAllegroApi(): TokenApi {
        return Retrofit.Builder()
            .baseUrl("https://allegro.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}