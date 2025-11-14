package com.flatcode.simpleadvancedapps.MainApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ItemMainInfoBinding

class MainInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<MainInfoAdapter.ViewHolder>() {

    private var binding: ItemMainInfoBinding? = null
    var list: ArrayList<MainInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainInfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MainInfo = list!![position]
        val name: String = model.title!!
        val s: Boolean = model.s!!
        val s2: Boolean = model.s2!!
        val s3: Boolean = model.s3!!
        val s4: Boolean = model.s4!!
        val s5: Boolean = model.s5!!
        val s6: Boolean = model.s6!!

        holder.name.text = name
        check(s, holder.daggerHilt)
        check(s2, holder.navigation)
        check(s3, holder.room)
        check(s4, holder.coroutines)
        check(s5, holder.rxJava)
        check(s6, holder.compose)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun addList(mainInfoList: ArrayList<MainInfo>?) {
        list = mainInfoList
        notifyDataSetChanged()
    }

    fun check(boolean: Boolean, image: ImageView) {
        if (boolean) image.setImageResource(R.drawable.circle_green)
        else image.setImageResource(R.drawable.circle_red)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var daggerHilt: ImageView
        var navigation: ImageView
        var room: ImageView
        var coroutines: ImageView
        var rxJava: ImageView
        var compose: ImageView

        init {
            name = binding!!.name
            daggerHilt = binding!!.daggerHilt
            navigation = binding!!.navigation
            room = binding!!.room
            coroutines = binding!!.coroutines
            rxJava = binding!!.rxJava
            compose = binding!!.compose
        }
    }

    init {
        this.list = list
    }
}