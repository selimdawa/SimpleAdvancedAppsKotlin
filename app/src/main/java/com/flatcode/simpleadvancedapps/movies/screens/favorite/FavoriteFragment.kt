package com.flatcode.simpleadvancedapps.movies.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.databinding.FragmentFavoriteMovieBinding
import com.flatcode.simpleadvancedapps.utils.DATA

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { FavoriteAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.toolbar.nameSpace.text = DATA.FAVORITE_MOVIES
        val viewModel = ViewModelProvider(this)[FavoriteFragmentViewModel::class.java]
        recyclerView = binding.rvFavorite
        recyclerView.adapter = adapter
        viewModel.getAllMovies().observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.asReversed())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}