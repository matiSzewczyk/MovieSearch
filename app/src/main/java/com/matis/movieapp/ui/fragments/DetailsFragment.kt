package com.matis.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matis.movieapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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

//        Glide.with(binding.moviePoster.context)
//            .load("https://image.tmdb.org/t/p/w500" + arguments?.get("poster").toString())
//            .into(binding.moviePoster)
        binding.movieTitle.text = arguments?.get("name").toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}