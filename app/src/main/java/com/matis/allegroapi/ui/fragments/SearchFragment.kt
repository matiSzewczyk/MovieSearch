package com.matis.allegroapi.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                            Log.d("SearchFragment", "Success: ${it.accessToken}")
                        }
                        is SearchViewModel.UiState.UiStatus.IsLoading -> {
                            // TODO: Display progressBar
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}