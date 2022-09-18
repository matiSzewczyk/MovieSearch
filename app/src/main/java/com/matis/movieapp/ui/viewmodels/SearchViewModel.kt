package com.matis.movieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.movieapp.data.models.offerResponse.OfferResponse
import com.matis.movieapp.data.sources.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    data class UiState(
        var responseList: MutableList<OfferResponse> = mutableListOf(),
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
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    status = UiState.UiStatus.IsLoading
                )
            }
        }
    }

    fun getOffers(
        sellerLogin: String
    ) {
        viewModelScope.launch {
            val response = repository.getOffers(
                uiState.value.accessToken.toString(),
                sellerLogin
            )
            if (response.isSuccessful) {
                Log.d(
                    "SearchViewModel", "getOffers: ${
                        response.body()!!.offers[0].name
                    }"
                )
            } else {
                val jsonObject = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d(
                    "SearchViewModel", "getOffers: ${jsonObject.getString("message")}"
                )
            }
        }
    }
}