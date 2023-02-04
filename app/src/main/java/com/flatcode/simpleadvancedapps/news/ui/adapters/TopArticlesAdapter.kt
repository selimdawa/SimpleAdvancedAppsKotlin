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
    ListAdapter<TopArticlesNewsItem, TopArticlesAdapter.TopArticlesViewHolder>(
        BaseDiffUtilItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArticlesViewHolder =
        TopArticlesViewHolder(
            ItemTopArticlesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TopArticlesViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    class TopArticlesViewHolder(private val binding: ItemTopArticlesBinding) :
        ViewHolder(binding.root) {
        fun onBind(model: TopArticlesNewsItem) {
            binding.ivCompany.load(model.image)
            binding.tvTittle.text = model.tittle
        }
    }
}