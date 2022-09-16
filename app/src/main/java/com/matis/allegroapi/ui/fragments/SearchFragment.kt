package com.matis.allegroapi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.matis.allegroapi.databinding.FragmentSearchBinding
import com.matis.allegroapi.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it.status) {
                        is SearchViewModel.UiState.UiStatus.Success -> {
                            binding.progressBar.isVisible = false
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
        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getOffers(
                    binding.searchBar.text.toString()
                )
                hideSoftKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun hideSoftKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}