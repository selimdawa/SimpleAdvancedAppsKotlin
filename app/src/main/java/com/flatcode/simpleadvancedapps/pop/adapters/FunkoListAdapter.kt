package com.flatcode.simpleadvancedapps.pop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.databinding.ItemPopBinding
import com.flatcode.simpleadvancedapps.pop.models.PopItem
import com.squareup.picasso.Picasso

class FunkoListAdapter(private val clickListener: PopListener) :
    ListAdapter<PopItem, FunkoListAdapter.FunkoListViewHolder>(DiffCallback) {

    class FunkoListViewHolder(private val binding: ItemPopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: PopListener, pop: PopItem) {
            binding.popName.text = pop.name

            binding.root.setOnClickListener {
                clickListener.onClick(pop)
            }

            Picasso.get()
                .load(pop.img)
                .resize(320, 320)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunkoListViewHolder {
        return FunkoListViewHolder(
            ItemPopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FunkoListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PopItem>() {
        override fun areItemsTheSame(oldItem: PopItem, newItem: PopItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopItem, newItem: PopItem): Boolean {
            return oldItem == newItem
        }
    }
}

class PopListener(val clickListener: (pop: PopItem) -> Unit) {
    fun onClick(pop: PopItem) = clickListener(pop)
}