package com.matis.movieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.movieapp.data.models.details.Details
import com.matis.movieapp.data.sources.details.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "DetailsViewModel"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    data class UiState(
        var poster: String? = null,
        var title: String? = null
    )

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    fun setUiData(name: String?, id: Int) {
        viewModelScope.launch {
            val response: Response<Details> = if (name == null) {
                repository.getMovie(id)
            } else {
                repository.getTvShow(id)
            }
            if (response.isSuccessful) {
                setTitle(response)
                setPoster(response)
                Log.d(TAG, "setUiData: ${response.body()!!}")
            } else {
                Log.e(TAG, "setUiData: ${response.errorBody()!!.charStream().readText()}")
            }
        }
    }

    private fun setPoster(response: Response<Details>) {
        val poster = response.body()!!.poster_path
        _uiState.update {
            it.copy(
                poster = poster
            )
        }
    }

    private fun setTitle(response: Response<Details>) {
        val title = if (response.body()!!.title == null) {
            response.body()!!.name
        } else {
            response.body()!!.title
        }
        _uiState.update {
            it.copy(
                title = title,
            )
        }
    }
}