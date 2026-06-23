package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory

class CategoryMealsAdapter : ListAdapter<MealsByCategory, CategoryMealsAdapter.CategoryMealsViewHolder>(MealDiffCallback) {

    var onItemClick: ((MealsByCategory) -> Unit)? = null

    class CategoryMealsViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meal = getItem(position)

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }

    private object MealDiffCallback : DiffUtil.ItemCallback<MealsByCategory>() {
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem == newItem
        }
    }
}