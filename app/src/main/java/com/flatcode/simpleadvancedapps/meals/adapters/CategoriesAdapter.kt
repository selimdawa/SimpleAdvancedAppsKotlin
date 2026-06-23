package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemCategoryMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.Category

class CategoriesAdapter : ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoryDiffCallback) {

    var onItemClick: ((Category) -> Unit)? = null

    class CategoryViewHolder(val binding: ItemCategoryMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)

        Glide.with(holder.itemView.context)
            .load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = category.strCategory

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }

    private object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}