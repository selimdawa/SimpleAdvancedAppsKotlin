package com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.flatcode.simpleadvancedapps.databinding.ItemEpisodeBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.models.episode.EpisodeModel

class EpisodeAdapter(private val list: ArrayList<EpisodeModel>) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            EpisodeViewHolder = EpisodeViewHolder(
        ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class EpisodeViewHolder(private val binding: ItemEpisodeBinding) : ViewHolder(binding.root) {
        fun onBind(model: EpisodeModel) {
            binding.tvNameEpisode.text = model.name
            binding.tvEpisode.text = model.episode
            binding.tvDate.text = model.airDate
            binding.tvCreated.text = model.created
        }
    }

    fun addNewItems(episodeModel: List<EpisodeModel>) {
        list.addAll(episodeModel)
        notifyDataSetChanged()
    }
}