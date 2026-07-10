package com.flatcode.simpleadvancedapps.calculator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorEntity
import com.flatcode.simpleadvancedapps.databinding.ItemCalculatorHistoryBinding

class CalculatorHistoryAdapter :
    ListAdapter<CalculatorEntity, CalculatorHistoryAdapter.HistoryViewHolder>(HistoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemCalculatorHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(private val binding: ItemCalculatorHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalculatorEntity) {
            binding.txtHistoryExpression.text = item.expression
            binding.txtHistoryResult.text = item.result
        }
    }

    companion object HistoryDiffCallback : DiffUtil.ItemCallback<CalculatorEntity>() {
        override fun areItemsTheSame(
            oldItem: CalculatorEntity, newItem: CalculatorEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CalculatorEntity, newItem: CalculatorEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}