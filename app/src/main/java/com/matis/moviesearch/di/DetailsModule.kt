package com.matis.moviesearch.di

import com.matis.moviesearch.data.sources.details.DetailsApi
import com.matis.moviesearch.data.sources.details.DetailsRepository
import com.matis.moviesearch.data.sources.details.DetailsRepositoryImpl
import com.matis.moviesearch.utils.Constants
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
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}