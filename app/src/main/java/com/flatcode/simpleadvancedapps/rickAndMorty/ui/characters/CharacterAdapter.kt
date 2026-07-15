package com.flatcode.simpleadvancedapps.rickAndMorty.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseAdapter
import com.flatcode.simpleadvancedapps.databinding.ItemCharacterBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.CharacterModel
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.rickAndMorty.utils.loadImage

class CharacterAdapter : BaseAdapter<CharacterModel, Int, ItemCharacterBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup) =
        ItemCharacterBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemCharacterBinding, item: CharacterModel) {
        binding.image.loadImage(item.image, blurTarget = binding.imageBlur)
        binding.tvNameCharacter.text = item.name
        binding.tvStatus.text = item.status
        binding.cvIndicator.setImageResource(if (item.status == DATA.ALIVE) R.color.green else R.color.red)
        binding.tvSpecies.text = item.species
        binding.tvType.text = if (item.type.isEmpty()) DATA.UNKNOWN else item.type
        binding.tvFirstSeen.text = item.gender
    }
}