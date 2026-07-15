package com.flatcode.simpleadvancedapps.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemMainBinding
import com.flatcode.simpleadvancedapps.utils.openActivity

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainViewHolder>() {

    private var list = ArrayList<Main>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model = list[position]
        val image = model.image
        val number = model.number
        val name = model.title
        val c = model.c ?: return

        with(holder.binding) {
            this.image.setImageResource(if (image != 0) image else R.drawable.ic_load)

            if (number != 0) {
                this.number.visibility = View.VISIBLE
                this.number.text = context.getString(R.string.number_placeholder, number)
            } else {
                this.number.visibility = View.GONE
            }
            this.name.text = name

            root.setOnClickListener { context.openActivity(c) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList(mainList: ArrayList<Main>?) {
        val newList = mainList ?: arrayListOf()
        val diffCallback = MainDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class MainViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MainDiffCallback(
    private val oldList: List<Main>,
    private val newList: List<Main>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}