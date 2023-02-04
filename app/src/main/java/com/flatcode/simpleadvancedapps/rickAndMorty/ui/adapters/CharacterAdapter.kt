package com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.VOID
import com.flatcode.simpleadvancedapps.databinding.ItemCharacterBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.models.character.CharacterModel

class CharacterAdapter(private val context: Context?, val list: ArrayList<CharacterModel?>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var binding: ItemCharacterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val name = DATA.EMPTY + item!!.name
        val status = DATA.EMPTY + item.status
        val species = DATA.EMPTY + item.species
        val type = DATA.EMPTY + item.type
        val gender = DATA.EMPTY + item.gender
        val image = DATA.EMPTY + item.image

        VOID.GlideRickAndMorty(context, image, binding!!.image, binding!!.imageBlur, 50)
        binding!!.tvNameCharacter.text = name
        binding!!.tvStatus.text = status
        if (status == DATA.Alive) binding!!.cvIndicator.setImageResource(R.color.green)
        else binding!!.cvIndicator.setImageResource(R.color.red)
        binding!!.tvSpecies.text = species
        if (type == DATA.EMPTY) binding!!.tvType.text = DATA.Unknown
        else binding!!.tvType.text = type
        binding!!.tvFirstSeen.text = gender
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    fun addNewItems(characterModel: List<CharacterModel>) {
        list.addAll(characterModel)
        notifyDataSetChanged()
    }
}