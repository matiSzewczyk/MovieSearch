package com.matis.movieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.matis.movieapp.data.models.discoverMovies.Result
import com.matis.movieapp.data.sources.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


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

}