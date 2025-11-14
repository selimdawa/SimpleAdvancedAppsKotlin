package com.flatcode.simpleadvancedapps.poke.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
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

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPokeBinding.bind(view)

        fun bind(pokeItem: PokeItem) {
            binding.tvId.text = pokeItem.formatId
            binding.tvName.text = pokeItem.name
            Picasso.get().load(pokeItem.img)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(binding.ivPokemon)

            view.setOnClickListener {
                onItemClickListener(pokeItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poke, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
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