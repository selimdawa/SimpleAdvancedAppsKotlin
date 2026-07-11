package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.Meal

class FavoritesMealsAdapter :
    ListAdapter<Meal, FavoritesMealsAdapter.FavoritesMealsAdapterViewHolder>(MealDiffCallback) {

    var onItemClick: ((Meal) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesMealsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        val meal = getItem(position)

        with(holder.binding) {
            Glide.with(root.context).load(meal.strMealThumb).into(imgMeal)

            tvMealName.text = meal.strMeal
            root.setOnClickListener { onItemClick?.invoke(meal) }
        }
    }

    class FavoritesMealsAdapterViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean = oldItem == newItem
    }
}