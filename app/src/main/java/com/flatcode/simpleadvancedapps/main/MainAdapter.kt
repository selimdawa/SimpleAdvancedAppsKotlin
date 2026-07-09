package com.flatcode.simpleadvancedapps.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemMainBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.VOID

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainViewHolder>() {

    private var list: ArrayList<Main>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model = list?.get(position) ?: return
        val image = model.image
        val number = model.number
        val name = model.title
        val c = model.c ?: return

        with(holder.binding) {
            this.image.setImageResource(if (image != 0) image else R.drawable.ic_load)

            if (number != 0) {
                this.number.visibility = View.VISIBLE
                this.number.text = "${DATA.EMPTY}$number"
            } else {
                this.number.visibility = View.GONE
            }
            this.name.text = name

            root.setOnClickListener { VOID.Intent1(context, c) }
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    fun addList(mainList: ArrayList<Main>?) {
        list = mainList
        notifyDataSetChanged()
    }
}

class MainViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)