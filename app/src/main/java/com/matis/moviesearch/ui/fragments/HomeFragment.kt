package com.matis.moviesearch.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.matis.moviesearch.databinding.FragmentHomeBinding
import com.matis.moviesearch.ui.adapters.MovieAdapter
import com.matis.moviesearch.ui.viewmodels.HomeViewModel
import com.matis.moviesearch.utils.CustomClickInterface
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), CustomClickInterface {

    private val viewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var tvShowsAdapter: MovieAdapter
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var topRatedTvShowsAdapter: MovieAdapter

    private lateinit var transition: Slide

    private lateinit var arrayAdapter: ArrayAdapter<String>

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMoviesRecyclerView()
        setupTvShowsRecyclerView()
        setupTopRatedMoviesRecyclerView()
        setupTopRatedTvShowsRecyclerView()

        collectUiData()

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
                    viewModel.searchMovies(searchInput.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }
            searchInput.setOnItemClickListener { _, _, position, _ ->
                navigateToDetailsFragment(
                    viewModel.autoCompleteUiState.value.searchResults[position].id,
                    null,
                )
                clearSearchInput()
            }
        }
    }

    private fun collectUiData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesUiState.collectLatest {
                    when (it.status) {
                        is HomeViewModel.UiStatus.Success -> {
                            binding.moviesProgressBar.isVisible = false
                            moviesAdapter.notifyDataSetChanged()
                        }
                        is HomeViewModel.UiStatus.IsLoading -> {
                            binding.moviesProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tvShowsUiState.collectLatest {
                    when (it.status) {
                        is HomeViewModel.UiStatus.Success -> {
                            binding.tvShowsProgressBar.isVisible = false
                            tvShowsAdapter.notifyDataSetChanged()
                        }
                        is HomeViewModel.UiStatus.IsLoading -> {
                            binding.tvShowsProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRatedMoviesUiState.collectLatest {
                    when (it.status) {
                        is HomeViewModel.UiStatus.Success -> {
                            binding.topRatedMoviesProgressBar.isVisible = false
                            topRatedMoviesAdapter.notifyDataSetChanged()
                        }
                        is HomeViewModel.UiStatus.IsLoading -> {
                            binding.topRatedMoviesProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRatedTvShowsUiState.collectLatest {
                    when (it.status) {
                        is HomeViewModel.UiStatus.Success -> {
                            binding.topRatedTvShowsProgressBar.isVisible = false
                            topRatedTvShowsAdapter.notifyDataSetChanged()
                        }
                        is HomeViewModel.UiStatus.IsLoading -> {
                            binding.topRatedMoviesProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.autoCompleteUiState.collectLatest {
                    when (it.status) {
                        is HomeViewModel.UiStatus.Success -> {
                            Log.d(
                                TAG,
                                "onViewCreated: ${viewModel.autoCompleteUiState.value.searchResults}"
                            )
                            setupAutoCompleteTv()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupAutoCompleteTv() {
        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            viewModel.autoCompleteUiState.value.autoCompleteList
        ).also {
            binding.searchInput.setAdapter(it)
        }
    }

    private fun setupMoviesRecyclerView() = binding.trendingMoviesRecyclerView.apply {
        moviesAdapter = MovieAdapter(
            viewModel.moviesUiState.value.trendingMovies,
            this@HomeFragment
        )
        adapter = moviesAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupTvShowsRecyclerView() = binding.trendingTvShowsRecyclerView.apply {
        tvShowsAdapter = MovieAdapter(
            viewModel.tvShowsUiState.value.trendingTvShows,
            this@HomeFragment
        )
        adapter = tvShowsAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupTopRatedMoviesRecyclerView() = binding.topRatedMoviesRecyclerView.apply {
        topRatedMoviesAdapter = MovieAdapter(
            viewModel.topRatedMoviesUiState.value.topRatedMovies,
            this@HomeFragment
        )
        adapter = topRatedMoviesAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupTopRatedTvShowsRecyclerView() = binding.topRatedTvShowsRecyclerView.apply {
        topRatedTvShowsAdapter = MovieAdapter(
            viewModel.topRatedTvShowsUiState.value.topRatedTvShows,
            this@HomeFragment
        )
        adapter = topRatedTvShowsAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showSearchInput() = binding.apply {
        transition.addTarget(searchInput)
        TransitionManager.beginDelayedTransition(searchLayout, transition)

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
        TransitionManager.beginDelayedTransition(searchLayout, transition)

        searchInput.isVisible = false
        searchButton.isVisible = true
        fragmentTitle.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickListener(id: Int, name: String?) {
        navigateToDetailsFragment(id, name)
    }

    private fun navigateToDetailsFragment(id: Int, name: String?) {
        val action = HomeFragmentDirections.actionSearchFragmentToMovieFragment(name, id)
        findNavController().navigate(action)
    }

}