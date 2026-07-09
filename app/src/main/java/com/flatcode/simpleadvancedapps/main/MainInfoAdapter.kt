package com.flatcode.simpleadvancedapps.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemMainInfoBinding

class MainInfoAdapter(private val context: Context) :
    ListAdapter<MainInfo, MainInfoViewHolder>(MainInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainInfoViewHolder {
        val binding = ItemMainInfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainInfoViewHolder, position: Int) {
        val model = getItem(position)

        with(holder.binding) {
            name.text = model.title

            check(model.s == true, daggerHilt)
            check(model.s2 == true, navigation)
            check(model.s3 == true, room)
            check(model.s4 == true, coroutines)
            check(model.s5 == true, rxJava)
        }
    }

    private fun check(boolean: Boolean, image: ImageView) {
        image.setImageResource(if (boolean) R.drawable.circle_green else R.drawable.circle_red)
    }

    companion object MainInfoDiffCallback : DiffUtil.ItemCallback<MainInfo>() {
        override fun areItemsTheSame(oldItem: MainInfo, newItem: MainInfo): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MainInfo, newItem: MainInfo): Boolean {
            return oldItem.s == newItem.s &&
                    oldItem.s2 == newItem.s2 &&
                    oldItem.s3 == newItem.s3 &&
                    oldItem.s4 == newItem.s4 &&
                    oldItem.s5 == newItem.s5
        }
    }
}

class MainInfoViewHolder(val binding: ItemMainInfoBinding) : RecyclerView.ViewHolder(binding.root)