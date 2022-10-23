package com.matis.moviesearch.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.moviesearch.data.models.discover.Result
import com.matis.moviesearch.data.sources.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
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

    data class TopRatedTvShowsUiState(
        var topRatedTvShows: MutableList<Result> = mutableListOf(),
        var status: UiStatus? = null,
    )

    data class AutoCompleteUiState(
        var searchResults: MutableList<com.matis.moviesearch.data.models.search.Result> = mutableListOf(),
        var autoCompleteList: MutableList<String> = mutableListOf(),
        var status: UiStatus? = null
    )

    private var _tvShowsUiState = MutableStateFlow(TvShowsUiState())
    val tvShowsUiState: StateFlow<TvShowsUiState> get() = _tvShowsUiState.asStateFlow()

    private var _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState: StateFlow<MoviesUiState> get() = _moviesUiState.asStateFlow()

    private var _topRatedMoviesUiState = MutableStateFlow(TopRatedMoviesUiState())
    val topRatedMoviesUiState: StateFlow<TopRatedMoviesUiState> get() = _topRatedMoviesUiState.asStateFlow()

    private var _topRatedTvShowsUiState = MutableStateFlow(TopRatedTvShowsUiState())
    val topRatedTvShowsUiState: StateFlow<TopRatedTvShowsUiState> get() = _topRatedTvShowsUiState.asStateFlow()

    private var _autoCompleteUiState = MutableStateFlow(AutoCompleteUiState())
    val autoCompleteUiState: StateFlow<AutoCompleteUiState> get() = _autoCompleteUiState.asStateFlow()

    init {
        getTrendingMovies()
        getTrendingTvShows()
        getTopRatedMovies()
        getTopRatedTvShows()
    }

    private fun getTopRatedTvShows() = viewModelScope.launch {
        _topRatedTvShowsUiState.update {
            it.copy(
                status = UiStatus.IsLoading
            )
        }
        val response = repository.getTopRatedTvShows()
        if (response.isSuccessful) {
            response.body()!!.results.map {
                _topRatedTvShowsUiState.value.topRatedTvShows.add(it)
            }
            _topRatedTvShowsUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
            Log.d(TAG, "getTopRatedTvShows: ${response.body()!!.results}")
        } else {
            Log.e(TAG, "getTopRatedTvShows: ${response.errorBody()!!.charStream().readText()}")
        }
    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMoviesUiState.update {
            it.copy(
                status = UiStatus.IsLoading
            )
        }
        val response = repository.getTopRatedMovies()
        if (response.isSuccessful) {
            response.body()!!.results.map {
                _topRatedMoviesUiState.value.topRatedMovies.add(it)
            }
            _topRatedMoviesUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
            Log.d(TAG, "getTopRatedMovies: ${response.body()!!.results}")
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
            response.body()!!.results.map {
                _moviesUiState.value.trendingMovies.add(it)
            }
            _moviesUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
            Log.d(TAG, "getTrendingMovies: ${response.body()!!.results}")
        } else {
            Log.e(TAG, "getTrendingMovies: ${response.errorBody()!!.charStream().readText()}")
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
            Log.d(TAG, "getTrendingTvShows: ${response.body()!!.results}")
        } else {
            Log.e(TAG, "getTrendingTvShows: ${response.errorBody()!!.charStream().readText()}")
        }
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        val response = repository.searchMovies(query)

        if (response.isSuccessful) {
            response.body()!!.results.map {
                _autoCompleteUiState.value.searchResults.add(it)
                _autoCompleteUiState.value.autoCompleteList.add(it.original_title)
            }
            _autoCompleteUiState.update {
                it.copy(
                    status = UiStatus.Success
                )
            }
            Log.d(TAG, "searchMovies: ${response.body()!!.results}")
        } else {
            Log.e(TAG, "searchMovies: ${response.errorBody()!!.charStream().readText()}")
        }
    }
}