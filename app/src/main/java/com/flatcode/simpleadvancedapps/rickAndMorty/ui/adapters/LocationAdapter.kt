package com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.simpleadvancedapps.models.location.LocationModel
import com.flatcode.simpleadvancedapps.databinding.ItemLocationBinding

class LocationAdapter(private val list: ArrayList<LocationModel>) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class LocationViewHolder(private val binding: ItemLocationBinding) : ViewHolder(binding.root) {
        fun onBind(model: LocationModel) {
            binding.tvLocation.text = model.name
            binding.tvUrl.text = model.url
        }
    }

    fun addNewItems(locationModel: List<LocationModel>) {
        list.addAll(locationModel)
        notifyDataSetChanged()
    }
}