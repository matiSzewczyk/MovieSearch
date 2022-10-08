package com.matis.movieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.matis.movieapp.data.sources.details.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {
}