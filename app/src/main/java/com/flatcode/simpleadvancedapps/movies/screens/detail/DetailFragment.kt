package com.flatcode.simpleadvancedapps.movies.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.DATA.IMAGE_MOVIE
import com.flatcode.simpleadvancedapps.utils.DATA.MAIN
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailMovieBinding
import com.flatcode.simpleadvancedapps.movies.SaveShared
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentMovie: MovieItemModel
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        arguments?.let { args ->
            BundleCompat.getSerializable(args, "movie", MovieItemModel::class.java)?.let {
                currentMovie = it
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.toolbar.nameSpace.text = DATA.Details_Movie
        val valueBool = SaveShared.getFavorite(MAIN, currentMovie.id.toString())
        val viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.imgDetailFavorite.setImageResource(
            if (isFavorite != valueBool) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )

        Glide.with(MAIN).load("$IMAGE_MOVIE${currentMovie.poster_path}")
            .placeholder(R.color.image_profile).into(binding.imgDetail)

        with(binding) {
            tvTitleDetail.text = currentMovie.title
            tvDateDetail.text = currentMovie.release_date
            tvDescription.text = currentMovie.overview

            imgDetailFavorite.setOnClickListener {
                isFavorite = if (isFavorite == valueBool) {
                    imgDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    SaveShared.setFavorite(MAIN, currentMovie.id.toString(), true)
                    viewModel.insert(currentMovie) {}
                    true
                } else {
                    imgDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.delete(currentMovie) {}
                    SaveShared.setFavorite(MAIN, currentMovie.id.toString(), false)
                    false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}