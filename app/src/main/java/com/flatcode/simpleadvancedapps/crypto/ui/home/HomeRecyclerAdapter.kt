package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simpleadvancedapps.Unit.DATA.IMAGE_CRYPTO
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.databinding.ItemCryptoBinding

class HomeRecyclerAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.MViewHolder>() {

    private val coins = mutableListOf<Data>()

    class MViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ItemClickListener, coin: Data) {
            binding.tvRowTitle.text = coin.name
            binding.tvRowSymbol.text = coin.symbol
            binding.tvRowValue.text = "$${coin.quote!!.uSD!!.price}"

            binding.ivRowImage.load("${IMAGE_CRYPTO}${coin.id}.png") {
                crossfade(true)
            }

            binding.root.setOnClickListener {
                listener.onItemClick(coin, binding.ivRowImage, binding.tvRowTitle, binding.tvRowSymbol)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCryptoBinding.inflate(layoutInflater, parent, false)
                return MViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MViewHolder.from(parent)

    override fun onBindViewHolder(holder: MViewHolder, position: Int) =
        holder.bind(listener, coins[position])

    override fun getItemCount() = coins.size

    fun setList(newList: List<Data>) {
        coins.clear()
        coins.addAll(newList)
        notifyDataSetChanged()
    }

    fun addMoreItems(moreList: List<Data>) {
        val startPosition = coins.size
        coins.addAll(moreList)
        notifyItemRangeInserted(startPosition, moreList.size)
    }
}