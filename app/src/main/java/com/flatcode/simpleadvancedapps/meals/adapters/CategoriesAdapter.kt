package com.flatcode.simpleadvancedapps.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.ItemCategoryMealBinding
import com.flatcode.simpleadvancedapps.meals.pojo.Category

class CategoriesAdapter :
    ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoryDiffCallback) {

    var onItemClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)

        with(holder.binding) {
            Glide.with(root.context).load(category.strCategoryThumb).into(imgCategory)

            tvCategoryName.text = category.strCategory
            root.setOnClickListener { onItemClick?.invoke(category) }
        }
    }

    class CategoryViewHolder(val binding: ItemCategoryMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.idCategory == newItem.idCategory

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }
}