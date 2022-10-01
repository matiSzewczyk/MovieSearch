package com.matis.movieapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.matis.movieapp.databinding.FragmentSearchBinding
import com.matis.movieapp.ui.adapters.MovieAdapter
import com.matis.movieapp.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MovieAdapter

    private lateinit var transition: Slide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transition = Slide(Gravity.END)
        transition.duration = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it.status) {
                        is SearchViewModel.UiState.UiStatus.Success -> {
                            binding.progressBar.isVisible = false
                            moviesAdapter.notifyDataSetChanged()
                            Log.d("SearchFragment", "Success: ${it.accessToken}")
                        }
                        is SearchViewModel.UiState.UiStatus.IsLoading -> {
                            Log.d(
                                "SearchFragment",
                                "onViewCreated: Progress bar visible"
                            )
                            binding.progressBar.isVisible = true
                        }
                        is SearchViewModel.UiState.UiStatus.Error -> {
                            Log.e(
                                "SearchFragment",
                                "Error: ${(it.status as SearchViewModel.UiState.UiStatus.Error).errorMessage}"
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }

        binding.apply {
            searchButton.setOnClickListener {
                showSearchInput()
            }

            searchInput.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideSearchInput()
                    hideSoftInput()
                } else {
                    showSoftInput()
                }
            }
            searchInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput()
                    clearSearchInput()
                    hideSearchInput()
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    private fun setupRecyclerView() = binding.trendingMoviesRecyclerView.apply {
        moviesAdapter = MovieAdapter(viewModel.uiState.value.recentTrendingMovies)
        adapter = moviesAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showSearchInput() = binding.apply {
        transition.addTarget(searchInput)
        TransitionManager.beginDelayedTransition(test, transition)

        searchInput.isVisible = true
        searchButton.isVisible = false
        fragmentTitle.isVisible = false
        searchInput.requestFocus()
    }

    private fun clearSearchInput() = binding.apply {
        searchInput.text.clear()
    }

    private fun showSoftInput() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(binding.searchInput, 0)
    }

    private fun hideSoftInput() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun hideSearchInput() = binding.apply {
        transition.addTarget(searchInput)
        TransitionManager.beginDelayedTransition(test, transition)

        searchInput.isVisible = false
        searchButton.isVisible = true
        fragmentTitle.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}