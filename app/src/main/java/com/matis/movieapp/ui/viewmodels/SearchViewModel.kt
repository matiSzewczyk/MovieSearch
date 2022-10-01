package com.matis.movieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.movieapp.data.models.discoverMovies.Result
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

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    data class UiState(
        var recentTrendingMovies: MutableList<Result> = mutableListOf(),
        var status: UiStatus? = null,
        var accessToken: String? = null
    ) {
        sealed class UiStatus {
            object IsLoading : UiStatus()
            object Success : UiStatus()
            data class Error(val errorMessage: String) : UiStatus()
        }
    }

    init {
        getRecentTrending()
    }

    private fun getRecentTrending() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                status = UiState.UiStatus.IsLoading
            )
        }
        val response = repository.getRecentTrendingMovies()
        if (response.isSuccessful) {
            response.body()!!.results.map {
                _uiState.value.recentTrendingMovies.add(it)
            }
            _uiState.update {
                it.copy(
                    status = UiState.UiStatus.Success
                )
            }
        } else {
            Log.e(TAG, "getRecentTrending: ${response.errorBody()!!.charStream().readText()}")
        }
    }

}