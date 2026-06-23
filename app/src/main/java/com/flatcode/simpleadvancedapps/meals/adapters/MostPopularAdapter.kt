package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemPopularMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory

class MostPopularAdapter : ListAdapter<MealsByCategory, MostPopularAdapter.PopularMealViewHolder>(PopularMealDiffCallback) {

    var onItemClick: ((MealsByCategory) -> Unit)? = null

    class PopularMealViewHolder(val binding: ItemPopularMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            ItemPopularMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val meal = getItem(position)

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }

    private object PopularMealDiffCallback : DiffUtil.ItemCallback<MealsByCategory>() {
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem == newItem
        }
    }
}