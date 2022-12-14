package com.matis.moviesearch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.matis.moviesearch.databinding.FragmentDetailsBinding
import com.matis.moviesearch.ui.viewmodels.DetailsViewModel
import com.matis.moviesearch.utils.Constants
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setUiData(
            arguments?.get("name") as String?,
            arguments?.get("id") as Int,
            savedInstanceState
        )

        collectUiValues()
    }

    private fun collectUiValues() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    binding.apply {

                        movieTitle.text = it.title

                        val multi = MultiTransformation(
                            BlurTransformation(20, 1),
                            CropTransformation(requireView().width, 750)
                        )

                        Glide.with(movieBackdrop.context)
                            .load(Constants.IMAGE_URL + it.backdrop)
                            .apply(RequestOptions.bitmapTransform(multi))
                            .into(movieBackdrop)

                        Glide.with(moviePoster.context)
                            .load(Constants.IMAGE_URL + it.poster)
                            .into(moviePoster)

                        ratingBar.rating = it.rating.toFloat()
                        ratingBar.isVisible = it.rating != 0.0

                        movieDescription.text = it.description

                        movieCountryValue.text = it.country
                        movieCountryTitle.isVisible = !(it.country.isNullOrEmpty())

                        movieYearValue.text = it.year
                        movieYearTitle.isVisible = !(it.year.isNullOrEmpty())

                        movieDurationValue.text = it.duration
                        movieDurationTitle.isVisible = !(it.duration.isNullOrEmpty())

                        movieGenres.text = it.genres?.joinToString {
                            it.name.toString()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}