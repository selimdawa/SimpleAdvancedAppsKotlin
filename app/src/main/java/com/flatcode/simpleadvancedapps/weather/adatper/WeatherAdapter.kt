package com.flatcode.simpleadvancedapps.weather.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simpleadvancedapps.databinding.ItemWeatherBinding
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel

class WeatherAdapter(
    private val onClick: (WeatherModel) -> Unit = {},
) : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(
        private val binding: ItemWeatherBinding, private val onClick: (WeatherModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherModel, isLast: Boolean) {
            with(binding) {
                root.setOnClickListener { onClick(item) }
                tvDate.text = item.time
                tvCondition.text = item.condition
                tvTemp.text = item.currentTemp.ifEmpty { "${item.maxTemp}°C / ${item.minTemp}°C" }
                imgListIcon.load("https:${item.imageUrl}")

                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    val density = root.context.resources.displayMetrics.density
                    bottomMargin = ((if (isLast) 10 else 5) * density).toInt()
                }
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean =
            oldItem.time == newItem.time

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), position == (itemCount - 1))
    }
}