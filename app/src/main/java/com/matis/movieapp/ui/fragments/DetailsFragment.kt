package com.matis.movieapp.ui.fragments

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
import com.matis.movieapp.R
import com.matis.movieapp.databinding.FragmentDetailsBinding
import com.matis.movieapp.ui.viewmodels.DetailsViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
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
            arguments?.get("id") as Int
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    binding.apply {

                        movieTitle.text = it.title

                        val multi = MultiTransformation(
                            BlurTransformation(20, 1),
                            CropTransformation(view.width, 750)
                        )

                        Glide.with(movieBackdrop.context)
                            .load("https://image.tmdb.org/t/p/w500" + it.backdrop)
                            .apply(RequestOptions.bitmapTransform(multi))
                            .into(movieBackdrop)

                        Glide.with(moviePoster.context)
                            .load("https://image.tmdb.org/t/p/w500" + it.poster)
                            .into(moviePoster)

                        ratingBar.rating = it.rating.toFloat()

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
        viewModel.close()
    }
}