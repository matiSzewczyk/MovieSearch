package com.matis.movieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.movieapp.data.models.Discover.Result
import com.matis.movieapp.data.sources.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    sealed class UiStatus {
        object IsLoading : UiStatus()
        object Success : UiStatus()
    }

    data class MoviesUiState(
        var trendingMovies: MutableList<Result> = mutableListOf(),
        var status: UiStatus? = null,
    )

    data class TvShowsUiState(
        var trendingTvShows: MutableList<Result> = mutableListOf(),
        var status: UiStatus? = null,
    )

    data class TopRatedMoviesUiState(
        var topRatedMovies: MutableList<Result> = mutableListOf(),
        var status: UiStatus? = null,
    )

    private var _tvShowsUiState = MutableStateFlow(TvShowsUiState())
    val tvShowsUiState: StateFlow<TvShowsUiState> get() = _tvShowsUiState.asStateFlow()

    private var _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState: StateFlow<MoviesUiState> get() = _moviesUiState.asStateFlow()

    private var _topRatedMoviesUiState = MutableStateFlow(TopRatedMoviesUiState())
    val topRatedMoviesUiState: StateFlow<TopRatedMoviesUiState> get() = _topRatedMoviesUiState.asStateFlow()

    init {
        getTrendingMovies()
        getTrendingTvShows()
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMoviesUiState.update {
            it.copy(
                status = UiStatus.IsLoading
            )
        }
        val response = repository.getTopRatedMovies()
        if (response.isSuccessful) {
            Log.d(TAG, "getTopRatedMovies: ${response.body()!!.results[0]}")
            response.body()!!.results.map {
                _topRatedMoviesUiState.value.topRatedMovies.add(it)
            }
            _topRatedMoviesUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
        } else {
            Log.e(TAG, "getTopRatedMovies: ${response.errorBody()!!.charStream().readText()}")
        }
    }

    private fun getTrendingMovies() = viewModelScope.launch {
        _moviesUiState.update {
            it.copy(
                status = UiStatus.IsLoading
            )
        }
        val response = repository.getTrendingMovies()
        if (response.isSuccessful) {
            Log.d(TAG, "getTrending: ${response.body()!!.results[0]}")
            response.body()!!.results.map {
                _moviesUiState.value.trendingMovies.add(it)
            }
            _moviesUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
        } else {
            Log.e(TAG, "getTrending: ${response.errorBody()!!.charStream().readText()}")
        }
    }

    private fun getTrendingTvShows() = viewModelScope.launch {
        _tvShowsUiState.update {
            it.copy(
                status = UiStatus.IsLoading
            )
        }
        val response = repository.getTrendingTvShows()
        if (response.isSuccessful) {
            response.body()!!.results.map {
                _tvShowsUiState.value.trendingTvShows.add(it)
            }
            _tvShowsUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
            Log.d(TAG, "getTrending: ${response.body()!!.results[0]}")
        } else {
            Log.e(TAG, "getTrending: ${response.errorBody()!!.charStream().readText()}")
        }
    }

}