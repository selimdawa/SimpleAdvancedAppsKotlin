package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory

class CategoryMealsAdapter :
    ListAdapter<MealsByCategory, CategoryMealsAdapter.CategoryMealsViewHolder>(MealDiffCallback) {

    var onItemClick: ((MealsByCategory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meal = getItem(position)

        with(holder.binding) {
            Glide.with(root.context).load(meal.strMealThumb).into(imgMeal)

            tvMealName.text = meal.strMeal
            root.setOnClickListener { onItemClick?.invoke(meal) }
        }
    }

    class CategoryMealsViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object MealDiffCallback : DiffUtil.ItemCallback<MealsByCategory>() {
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(
            oldItem: MealsByCategory, newItem: MealsByCategory
        ): Boolean = oldItem == newItem
    }
}