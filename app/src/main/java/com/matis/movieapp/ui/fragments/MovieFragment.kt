package com.matis.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.matis.movieapp.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.moviePoster.context)
            .load("https://image.tmdb.org/t/p/w500" + arguments?.get("poster").toString())
            .into(binding.moviePoster)
        binding.movieTitle.text = arguments?.get("id").toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}