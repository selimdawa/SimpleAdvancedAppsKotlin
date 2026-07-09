package com.flatcode.simpleadvancedapps.movies.screens.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.DATA.IMAGE_MOVIE_BASIC
import com.flatcode.simpleadvancedapps.utils.DATA.MAIN
import com.flatcode.simpleadvancedapps.databinding.ItemMovieBinding
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

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

            Glide.with(MAIN)
                .load("$IMAGE_MOVIE_BASIC${model.poster_path}")
                .placeholder(R.color.image_profile)
                .into(itemImg)
        }
    }

    override fun onViewAttachedToWindow(holder: FavoriteViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                FavoriteFragment.clickMovie(getItem(position))
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