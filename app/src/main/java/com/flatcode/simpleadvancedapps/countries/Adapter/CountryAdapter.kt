package com.flatcode.simpleadvancedapps.countries.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.countries.Fragments.DashboardFragmentDirections
import com.flatcode.simpleadvancedapps.countries.Model.Country
import com.flatcode.simpleadvancedapps.countries.Util.downloadFromUrl
import com.flatcode.simpleadvancedapps.countries.Util.placeholderProgressBar
import com.flatcode.simpleadvancedapps.databinding.ItemCountryBinding

class CountryAdapter(private val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val model = countryList[position]
        val context = holder.binding.root.context

        with(holder.binding) {
            cName.text = model.countryName
            dName.text = model.countryRegion

            item.setOnClickListener { view ->
                val action = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(model.uuid)
                Navigation.findNavController(view).navigate(action)
            }

            imageName.downloadFromUrl(
                false,
                model.imageURL,
                placeholderProgressBar(context)
            )

            imageBlur.downloadFromUrl(
                true,
                model.imageURL,
                placeholderProgressBar(context)
            )
        }
    }

    override fun getItemCount(): Int = countryList.size

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}

class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)