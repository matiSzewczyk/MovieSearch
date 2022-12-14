package com.matis.moviesearch.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.moviesearch.data.models.details.Details
import com.matis.moviesearch.data.models.details.Genre
import com.matis.moviesearch.data.sources.details.DetailsRepository
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
        var id: Int? = null,
        var poster: String? = null,
        var backdrop: String? = null,
        var title: String? = null,
        var rating: Double = 0.0,
        var description: String? = null,
        var duration: String? = null,
        var country: String? = null,
        var year: String? = null,
        var genres: MutableList<Genre>? = null
    )

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    fun setUiData(name: String?, id: Int, savedInstanceState: Bundle?) {
        if (!shouldReload(id, savedInstanceState)) return
        viewModelScope.launch {
            val response: Response<Details> = if (name == null) {
                repository.getMovie(id)
            } else {
                repository.getTvShow(id)
            }
            if (response.isSuccessful) {
                setId(id)
                setTitle(response)
                setPoster(response)
                setBackdrop(response)
                setRating(response)
                setDescription(response)
                setDuration(response)
                setCountry(response)
                setYear(response)
                setGenres(response)
                Log.d(TAG, "setUiData: ${response.body()!!}")
            } else {
                Log.e(TAG, "setUiData: ${response.errorBody()!!.charStream().readText()}")
            }
        }
    }

    private fun setId(id: Int) {
        _uiState.update {
            it.copy(
                id = id
            )
        }
    }

    private fun shouldReload(id: Int, savedInstanceState: Bundle?): Boolean {
        return uiState.value.id != id && savedInstanceState == null
    }

    private fun setGenres(response: Response<Details>) {
        val genres: MutableList<Genre> = mutableListOf()
        response.body()!!.genres?.map {
            genres.add(it)
        }
        _uiState.update {
            it.copy(
                genres = genres
            )
        }
    }

    private fun setYear(response: Response<Details>) {
        var year = response.body()!!.first_air_date
        if (year == null) {
            year = response.body()!!.release_date
        }
        year = year?.take(4)
        _uiState.update {
            it.copy(
                year = year.toString()
            )
        }
    }

    private fun setCountry(response: Response<Details>) {
        var country = response.body()!!.origin_country?.get(0)
        if (country == null) {
            country = response.body()!!.production_companies?.get(0)?.origin_country
        }
        _uiState.update {
            it.copy(
                country = country.toString()
            )
        }
    }

    private fun setBackdrop(response: Response<Details>) {
        val backdrop = response.body()!!.backdrop_path
        _uiState.update {
            it.copy(
                backdrop = backdrop
            )
        }
    }

    private fun setDuration(response: Response<Details>) {
        var duration = response.body()!!.runtime.toString()
        if (duration == "null") {
            duration = "~" + response.body()!!.last_episode_to_air?.runtime.toString()
        }
        duration += " min"
        _uiState.update {
            it.copy(
                duration = duration
            )
        }
    }

    private fun setDescription(response: Response<Details>) {
        val description = response.body()!!.overview
        _uiState.update {
            it.copy(
                description = description
            )
        }
    }

    private fun setRating(response: Response<Details>) {
        val rating = response.body()!!.vote_average
        _uiState.update {
            it.copy(
                rating = rating!!
            )
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