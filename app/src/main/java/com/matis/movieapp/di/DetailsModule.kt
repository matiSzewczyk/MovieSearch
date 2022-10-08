package com.matis.movieapp.di

import com.matis.movieapp.data.sources.details.DetailsApi
import com.matis.movieapp.data.sources.details.DetailsRepository
import com.matis.movieapp.data.sources.details.DetailsRepositoryImpl
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
class DetailsModule {

    @Provides
    @Singleton
    fun provideDetailsRepository(
        detailsApi: DetailsApi
    ): DetailsRepository {
        return DetailsRepositoryImpl(detailsApi)
    }

    @Provides
    @Singleton
    fun provideMovieDbApi(): DetailsApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}