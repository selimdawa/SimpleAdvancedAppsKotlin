package com.flatcode.simpleadvancedapps.news.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.flatcode.simpleadvancedapps.databinding.ItemTopArticlesBinding
import com.flatcode.simpleadvancedapps.news.base.BaseDiffUtilItemCallback
import com.flatcode.simpleadvancedapps.news.models.toparticles.TopArticlesNewsItem

class TopArticlesAdapter :
    ListAdapter<TopArticlesNewsItem, TopArticlesViewHolder>(BaseDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArticlesViewHolder {
        val binding = ItemTopArticlesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopArticlesViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }
}

class TopArticlesViewHolder(private val binding: ItemTopArticlesBinding) : ViewHolder(binding.root) {

    fun onBind(model: TopArticlesNewsItem) {
        with(binding) {
            ivCompany.load(model.image)
            tvTittle.text = model.tittle
        }
    }
}