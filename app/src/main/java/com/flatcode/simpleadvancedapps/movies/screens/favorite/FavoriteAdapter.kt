package com.flatcode.simpleadvancedapps.movies.screens.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemMovieBinding
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel
import com.flatcode.simpleadvancedapps.utils.DATA.IMAGE_MOVIE_BASIC

class FavoriteAdapter(private val context: Context) :
    ListAdapter<MovieItemModel, FavoriteViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val model = getItem(position)

        with(holder.binding) {
            tvTitle.text = model.title
            tvDate.text = model.release_date

            Glide.with(context).load("$IMAGE_MOVIE_BASIC${model.poster_path}")
                .placeholder(R.color.image_profile).into(itemImg)
        }
    }

    override fun onViewAttachedToWindow(holder: FavoriteViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { view ->
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = getItem(position)
                val bundle = android.os.Bundle().apply {
                    putSerializable("movie", movie)
                }
                view.findNavController()
                    .navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: FavoriteViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    private object MovieDiffCallback : DiffUtil.ItemCallback<MovieItemModel>() {
        override fun areItemsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean =
            oldItem == newItem
    }
}

class FavoriteViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)