package com.flatcode.simpleadvancedapps.rickAndMorty.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : IBaseDiffModel<S>, S, VB : ViewBinding>(
    itemCallback: BaseDiffUtilItemCallback<T, S> = BaseDiffUtilItemCallback()
) : ListAdapter<T, BaseAdapter<T, S, VB>.ViewHolder>(itemCallback) {

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflateBinding(LayoutInflater.from(parent.context), parent))
    }

    abstract fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder.binding, getItem(position))
    }

    abstract fun bind(binding: VB, item: T)
}