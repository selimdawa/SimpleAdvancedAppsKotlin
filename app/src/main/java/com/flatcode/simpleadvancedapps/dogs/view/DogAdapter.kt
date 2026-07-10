package com.flatcode.simpleadvancedapps.dogs.view

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemListBinding

class DogAdapter : ListAdapter<String, DogViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}

class DogViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: String) {
        binding.imageView.load(photo) {
            crossfade(true)
            crossfade(300)
            placeholder(R.drawable.loading_animation)
            error(R.color.image_profile)
            listener(onStart = { _ ->
                (binding.imageView.drawable as? Animatable)?.start()
            }, onSuccess = { _, _ ->
                (binding.imageView.drawable as? Animatable)?.stop()
            }, onError = { _, _ ->
                (binding.imageView.drawable as? Animatable)?.stop()
            })
        }
    }
}