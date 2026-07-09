package com.flatcode.simpleadvancedapps.movies.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.DATA.MAIN
import com.flatcode.simpleadvancedapps.databinding.FragmentMainMovieBinding
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { MainAdapter() }

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
        with(binding.toolbar) {
            nameSpace.text = DATA.MOVIE
            imageLeft.visibility = View.VISIBLE
            imageLeft.setImageResource(R.drawable.ic_baseline_favorite_24)
            imageLeft.setOnClickListener {
                MAIN.navController.navigate(R.id.action_mainFragment_to_favoriteFragment)
            }
        }

        val viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        viewModel.initDatabase()
        recyclerView = binding.rvMain
        recyclerView.adapter = adapter
        viewModel.getMoviesRetrofit()
        viewModel.myMovies.observe(viewLifecycleOwner) { list ->
            list.body()?.results?.let { results ->
                adapter.submitList(results)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun clickMovie(model: MovieItemModel) {
            val bundle = Bundle().apply {
                putSerializable("movie", model)
            }
            MAIN.navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }
}