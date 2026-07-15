package com.flatcode.simpleadvancedapps.rickAndMorty.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseAdapter
import com.flatcode.simpleadvancedapps.databinding.ItemEpisodeBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.EpisodeModel

class EpisodeAdapter : BaseAdapter<EpisodeModel, Int, ItemEpisodeBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup) =
        ItemEpisodeBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemEpisodeBinding, item: EpisodeModel) {
        binding.tvNameEpisode.text = item.name
        binding.tvEpisode.text = item.episode
        binding.tvDate.text = item.airDate
        binding.tvCreated.text = item.created
    }
}