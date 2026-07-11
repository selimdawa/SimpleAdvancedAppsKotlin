package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemPopularMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory

class MostPopularAdapter :
    ListAdapter<MealsByCategory, MostPopularAdapter.PopularMealViewHolder>(PopularMealDiffCallback) {

    var onItemClick: ((MealsByCategory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val binding =
            ItemPopularMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val meal = getItem(position)

        with(holder.binding) {
            Glide.with(root.context).load(meal.strMealThumb).into(imgPopularMealItem)

            root.setOnClickListener { onItemClick?.invoke(meal) }
        }
    }

    class PopularMealViewHolder(val binding: ItemPopularMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object PopularMealDiffCallback : DiffUtil.ItemCallback<MealsByCategory>() {
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(
            oldItem: MealsByCategory, newItem: MealsByCategory
        ): Boolean = oldItem == newItem
    }
}