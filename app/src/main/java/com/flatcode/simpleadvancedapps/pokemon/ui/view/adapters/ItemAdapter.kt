package com.flatcode.simpleadvancedapps.pokemon.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemPokeBinding
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem

class ItemAdapter : ListAdapter<PokeItem, ItemAdapter.ViewHolder>(DiffCallBack) {

    lateinit var onItemClickListener: (PokeItem) -> Unit

    inner class ViewHolder(private val binding: ItemPokeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokeItem: PokeItem) {
            binding.tvId.text = pokeItem.formatId
            binding.tvName.text = pokeItem.name

            binding.ivPokemon.load(pokeItem.img) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
                crossfade(true)
            }

            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(pokeItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<PokeItem>() {
        override fun areItemsTheSame(oldItem: PokeItem, newItem: PokeItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PokeItem, newItem: PokeItem): Boolean =
            oldItem == newItem
    }
}