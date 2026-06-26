package com.flatcode.simpleadvancedapps.movies.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA.IMAGE_MOVIE_BASIC
import com.flatcode.simpleadvancedapps.Unit.DATA.MAIN
import com.flatcode.simpleadvancedapps.databinding.ItemMovieBinding
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

class MainAdapter : ListAdapter<MovieItemModel, MainViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
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

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                MainFragment.clickMovie(getItem(position))
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: MainViewHolder) {
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

class MainViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)