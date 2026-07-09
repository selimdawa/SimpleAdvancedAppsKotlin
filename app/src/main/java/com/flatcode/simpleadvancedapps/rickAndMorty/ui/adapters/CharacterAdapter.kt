package com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.VOID
import com.flatcode.simpleadvancedapps.databinding.ItemCharacterBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.models.CharacterModel

class CharacterAdapter(private val context: Context?, val list: ArrayList<CharacterModel?>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        if (item != null) {
            holder.bind(context, item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context?, item: CharacterModel) {
            val name = DATA.EMPTY + item.name
            val status = DATA.EMPTY + item.status
            val species = DATA.EMPTY + item.species
            val type = DATA.EMPTY + item.type
            val gender = DATA.EMPTY + item.gender
            val image = DATA.EMPTY + item.image

            VOID.GlideRickAndMorty(context, image, binding.image, binding.imageBlur, 50)
            binding.tvNameCharacter.text = name
            binding.tvStatus.text = status

            if (status == DATA.Alive) {
                binding.cvIndicator.setImageResource(R.color.green)
            } else {
                binding.cvIndicator.setImageResource(R.color.red)
            }

            binding.tvSpecies.text = species

            if (type == DATA.EMPTY) {
                binding.tvType.text = DATA.Unknown
            } else {
                binding.tvType.text = type
            }

            binding.tvFirstSeen.text = gender
        }
    }

    fun addNewItems(characterModel: List<CharacterModel>) {
        val startPosition = list.size
        list.addAll(characterModel)
        notifyItemRangeInserted(startPosition, characterModel.size)
    }
}