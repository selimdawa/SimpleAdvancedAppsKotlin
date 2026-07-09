package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simpleadvancedapps.utils.DATA.IMAGE_CRYPTO
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

            val price = coin.quote?.uSD?.price
            binding.tvRowValue.text = if (price != null) "$$price" else ""

            val coinId = coin.id
            if (coinId != null) {
                binding.ivRowImage.load("$IMAGE_CRYPTO$coinId.png")
            } else {
                binding.ivRowImage.setImageDrawable(null)
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
        val diffCallback = CryptoDiffCallback(coins, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        coins.clear()
        coins.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    private class CryptoDiffCallback(
        private val oldList: List<Data>,
        private val newList: List<Data>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}