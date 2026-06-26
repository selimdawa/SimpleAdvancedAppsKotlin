package com.flatcode.simpleadvancedapps.poke.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemPokeBinding
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItem
import com.squareup.picasso.Picasso

class ItemAdapter : ListAdapter<PokeItem, ItemAdapter.ViewHolder>(DiffCallBack) {

    lateinit var onItemClickListener: (PokeItem) -> Unit

    class ViewHolder(private val binding: ItemPokeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokeItem: PokeItem, onItemClickListener: (PokeItem) -> Unit) {
            binding.tvId.text = pokeItem.formatId
            binding.tvName.text = pokeItem.name

            Picasso.get().load(pokeItem.img)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(binding.ivPokemon)

            binding.root.setOnClickListener {
                onItemClickListener(pokeItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<PokeItem>() {
        override fun areItemsTheSame(oldItem: PokeItem, newItem: PokeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokeItem, newItem: PokeItem): Boolean {
            return oldItem == newItem
        }
    }
}