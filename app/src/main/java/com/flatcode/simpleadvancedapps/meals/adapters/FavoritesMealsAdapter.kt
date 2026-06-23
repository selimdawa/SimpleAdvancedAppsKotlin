package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.Meal

class FavoritesMealsAdapter : ListAdapter<Meal, FavoritesMealsAdapter.FavoritesMealsAdapterViewHolder>(MealDiffCallback) {

    var onItemClick: ((Meal) -> Unit)? = null

    class FavoritesMealsAdapterViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoritesMealsAdapterViewHolder {
        return FavoritesMealsAdapterViewHolder(
            ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        val meal = getItem(position)

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }

    private object MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}