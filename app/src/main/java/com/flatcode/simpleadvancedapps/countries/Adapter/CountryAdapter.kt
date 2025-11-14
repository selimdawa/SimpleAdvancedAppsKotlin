package com.flatcode.simpleadvancedapps.countries.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.countries.Fragments.DashboardFragmentDirections
import com.flatcode.simpleadvancedapps.countries.Model.Country
import com.flatcode.simpleadvancedapps.countries.Util.downloadFromUrl
import com.flatcode.simpleadvancedapps.countries.Util.placeholderProgressBar
import com.flatcode.simpleadvancedapps.databinding.ItemCountryBinding

class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var binding: ItemCountryBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Country = countryList[position]
        val c_Name: String = model.countryName!!
        val d_Name: String = model.countryRegion!!

        holder.cName.text = c_Name
        holder.dName.text = d_Name

        holder.item.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(countryList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }

        holder.imageName.downloadFromUrl(
            false, countryList[position].imageURL,
            placeholderProgressBar(holder.item.context)
        )

        holder.imageBlur.downloadFromUrl(
            true, countryList[position].imageURL,
            placeholderProgressBar(holder.item.context)
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cName: TextView
        var dName: TextView
        var imageName: ImageView
        var imageBlur: ImageView
        var item: LinearLayout

        init {
            cName = binding!!.cName
            dName = binding!!.dName
            imageName = binding!!.imageName
            imageBlur = binding!!.imageBlur
            item = binding!!.item
        }
    }
}