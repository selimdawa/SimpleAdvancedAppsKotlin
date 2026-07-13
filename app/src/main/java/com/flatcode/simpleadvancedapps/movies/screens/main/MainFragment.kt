package com.flatcode.simpleadvancedapps.movies.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.flatcode.simpleadvancedapps.databinding.FragmentMainMovieBinding
import com.flatcode.simpleadvancedapps.movies.models.MoviesUiState
import com.flatcode.simpleadvancedapps.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph_movie)

    private val adapter by lazy {
        MainAdapter { movie ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.toolbar.nameSpace.text = getString(R.string.movies)
        binding.toolbar.imageLeft.visibility = View.VISIBLE
        binding.toolbar.imageLeft.setImageResource(R.drawable.ic_baseline_favorite_24)

        binding.toolbar.imageLeft.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToFavoriteFragment()
            findNavController().navigate(action)
        }

        binding.rvMain.adapter = adapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MoviesUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is MoviesUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.listMovies = state.movies
                }

                is MoviesUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}