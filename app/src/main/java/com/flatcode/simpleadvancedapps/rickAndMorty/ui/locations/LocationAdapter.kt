package com.flatcode.simpleadvancedapps.rickAndMorty.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseAdapter
import com.flatcode.simpleadvancedapps.databinding.ItemLocationBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.LocationModel

class LocationAdapter : BaseAdapter<LocationModel, Int, ItemLocationBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup) =
        ItemLocationBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemLocationBinding, item: LocationModel) {
        binding.tvLocation.text = item.name
        binding.tvUrl.text = item.url
    }
}